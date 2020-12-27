(ns advent-of-code-2020.day20
  (:require
   [advent-of-code-2020.utils :as u]
   [clojure.string :as string]))

(defn- read-tile-id [tile-id]
  (Long/parseLong (re-find #"\d+" tile-id)))

(defn- read-image [image]
  (let [width (count (first image))
        height (count image)]
    (-> (reduce (fn [im [y image-row]]
                  (reduce (fn [im [x pixel]]
                            (if (= \# pixel)
                              (assoc! im [x y] 1)
                              im))
                          im (mapv vector (range) image-row)))
                (transient {})
                (mapv vector (range) image))
        (assoc! ,,, :width width)
        (assoc! ,,, :height height)
        persistent!)))

(defn- read-tile [tile]
  (let [[tile-id & image] (string/split-lines tile)]
    {:tile-id (read-tile-id tile-id)
     :image (read-image image)}))

(defn- read-input [s]
  (mapv read-tile (u/split-sections s)))

(defn- serialize-border [border]
  [(read-string (string/join (conj border "2r"))) ; Ordinary value
   (read-string (string/join (conj (reverse border) "2r")))]) ; Flipped value

(defn- remove-border! [{:keys [^long width ^long height] :as image}]
  (reduce (fn [i [x y]] (dissoc! i [x y]))
          image
          (concat (mapv vector (range width) (repeat 0)) ; North
                  (mapv vector (repeat (dec width)) (range height)) ; East
                  (mapv vector (range width) (repeat (dec height))) ; South
                  (mapv vector (repeat 0) (range height))))) ; West

(defn- fix-coords [{:keys [width height] :as content}]
  (persistent!
   (reduce (fn [c [^long x ^long y]]
             (assoc! c [(dec x) (dec y)] 1))
           (transient {:width width :height height})
           (remove #{:width :height} (keys content)))))

(defn- extract-content [{:keys [^long width ^long height] :as image}]
  (-> image
      transient
      remove-border!
      (assoc! ,,, :width (- width 2))
      (assoc! ,,, :height (- height 2))
      persistent!
      fix-coords))

(defn- serialize-image [{:keys [^long width ^long height] :as image}]
  (let [north (map (fn [x y] (get image [x y] 0))
                   (range width) (repeat 0))
        east (map (fn [x y] (get image [x y] 0))
                  (repeat (dec width)) (range height))
        south (map (fn [x y] (get image [x y] 0))
                   (range width) (repeat (dec height)))
        west (map (fn [x y] (get image [x y] 0))
                  (repeat 0) (range height))]
    {:north (serialize-border north)
     :east (serialize-border east)
     :south (serialize-border south)
     :west (serialize-border west)
     :content (extract-content image)
     :flipped false}))

(defn- flip-border [{:keys [north east south west flipped] :as tile}]
  (-> tile
      transient
      (assoc! ,,, :north south)
      (assoc! ,,, :south north)
      (assoc! ,,, :east (reverse east))
      (assoc! ,,, :west (reverse west))
      ;; Remember that we flipped the border
      (assoc! ,,, :flipped (not flipped))
      persistent!))

(defn- rotate-border [{:keys [north west south east rotation] :as tile}]
  (-> tile
      transient
      (assoc! ,,, :north east)
      (assoc! ,,, :east (reverse south))
      (assoc! ,,, :south west)
      (assoc! ,,, :west (reverse north))
      ;; Remember the rotation to rotate the content later
      (assoc! ,,, :rotation (if rotation (+ ^long rotation 90) 90))
      persistent!))

(defn- possible-transforms [tile]
  (let [rots (take 4 (iterate #(update % :image rotate-border) tile))]
    (concat rots (mapv #(update % :image flip-border) rots))))

(defn- get-border-values [tile]
  (let [bs ((juxt :north :east :south :west) tile)]
    (mapv first bs)))

(defn- collect-borders [image-tiles]
  (eduction
   (comp (mapcat possible-transforms)
         (map :image)
         (mapcat get-border-values))
   image-tiles))

(defn- find-corners [image-tiles]
  (loop [[corner-candidate & corner-candidates] image-tiles
         corners []]
    (let [other-borders (collect-borders (remove #{corner-candidate} image-tiles))]
      (cond
        ;; We are done
        (nil? corner-candidate) corners
        ;; We found a corner piece
        (= 2 (count (remove (set other-borders) (get-border-values (:image corner-candidate)))))
        (recur corner-candidates (conj corners corner-candidate))
        ;; We found a tile thats not a corner piece
        :else (recur corner-candidates corners)))))

(defonce ^:private image-tiles
  (read-input (slurp "resources/image_tiles.txt")))

(defn day20-1
  ([] (day20-1 image-tiles))
  ([image-tiles]
   (transduce
    (map :tile-id)
    *
    (find-corners (mapv #(update % :image serialize-image) image-tiles)))))

(defn- get-border [border tile]
  (-> tile border first))

(defonce ^:private opposite
  {:north :south
   :east :west
   :south :north
   :west :east})

(defn- possible-neighbors [tile border tiles]
  (let [opp (opposite border)
        b (-> tile :image ((partial get-border border)))]
    (transduce
     (comp (remove (fn [{:keys [tile-id]}] (= (:tile-id tile) tile-id)))
           (mapcat possible-transforms)
           (filter (fn [{:keys [image]}] (= b (get-border opp image)))))
     conj
     tiles)))

(defn- top-left-tiles [tiles]
  (eduction
   (comp (mapcat possible-transforms)
         (filter #(and (seq (possible-neighbors % :east tiles))
                       (seq (possible-neighbors % :south tiles)))))
   (find-corners tiles)))

(defn- rotate
  ([[^double x0 ^double y0] [^long x ^long y] ^long deg]
   (let [[^double xx ^double yy] (rotate [(- x x0) (- y y0)] deg)]
     [(long (+ xx x0)) (long (+ yy y0))]))
  ([[^double x y] ^long deg]
   (if (pos? deg)
     (recur [y (- x)] (- deg 90))
     [x y])))

(defn- rotate-content [{:keys [image] :as image-tile}]
  (let [{:keys [rotation content]} image
        width (:width content)
        height (:height content)]
    (if rotation
      (assoc-in image-tile [:image :content]
                (persistent!
                 (reduce (fn [c [p]]
                           (assoc! c (rotate [(/ (dec ^long width) 2) (/ (dec ^long height) 2)] p rotation) 1))
                         (transient {:width width :height height})
                         (dissoc content :width :height))))
      image-tile)))

(defn- flip [[x ^long y] ^long height]
  [x (- (dec height) y)])

(defn- flip-content [{:keys [image] :as image-tile}]
  (let [{:keys [flipped content]} image
        width (:width content)
        height (:height content)]
    (if flipped
      (assoc-in image-tile [:image :content]
                (persistent!
                 (reduce (fn [c [p]] (assoc! c (flip p height) 1))
                         (transient {:width width :height height})
                         (dissoc content :width :height))))
      image-tile)))

(defn- adjust-content [image-row]
  (eduction
   (map (fn [image] (flip-content (rotate-content image))))
   image-row))

(defn- image-tile->row-strings [{:keys [image]}]
  (let [content (:content image)
        width (-> content :width)
        height (-> content :height)]
    (mapv string/join
          (for [y (range height)]
            (for [x (range width)]
              (if (get content [x y]) "#" "."))))))

(defn- stitch-row-together [image-row]
  (string/join "\n"
               (apply mapv str
                      (mapv image-tile->row-strings image-row))))

(defn- stitch-image-together [image-rows]
  (string/join "\n" (mapv stitch-row-together image-rows)))

(defn- connect-strip [tile direction image-tiles]
  (loop [tile tile
         [neighbor] (possible-neighbors tile direction image-tiles)
         strip [tile]]
    (if neighbor
      ;; We found a tile that fits in this direction
      (recur neighbor
             (possible-neighbors neighbor direction image-tiles)
             (conj strip neighbor))
      ;; There are no more tiles that fit, return the strip
      strip)))

(defn- sea-monster [spacing]
  (re-pattern
   (str
    ;; We use the dotall mode (?s), so '.' also matches newline characters
    "(?s)"
    ;; We put the space between the monster in matching groups in order
    ;; to only replace the monsters body and keep the space between
    "(..................)#(..{" spacing "})"
    "#(....)##(....)##(....)###(.{" spacing "}"
    ".)#(..)#(..)#(..)#(..)#(..)#")))

(defonce ^:private mark-sea-monster
  ;; We keep the space we put into matching groups the same and mark the monster
  ;; with O characters
  "$1O$2O$3OO$4OO$5OOO$6O$7O$8O$9O$10O$11O")

(defn- build-image [image-tiles from]
  (persistent!
   (transduce
    (comp (map (fn [edge-tile] (connect-strip edge-tile :east image-tiles)))
          (map adjust-content))
    conj!
    ;; We first form the west edge and then build the image from there
    (connect-strip from :south image-tiles))))

(defn- fix-replace [s match replacement]
  ;; Replace the match with a replacement until no match is found anymore
  (let [n (string/replace s match replacement)]
    (if (= n s) n (recur n match replacement))))

(defn day20-2
  ([] (day20-2 image-tiles 77))
  ([image-tiles spacing]
   (let [image-tiles (mapv (fn [it] (update it :image serialize-image))
                           image-tiles)]
     (transduce
      (comp (map stitch-image-together)
            (map (fn [im] (fix-replace im (sea-monster spacing) mark-sea-monster)))
            (map #(filter #{\#} %))
            (map count))
      min
      Long/MAX_VALUE
      ;; Build the 8 flipped and rotated images in parallel, one of which is the
      ;; correct image. This is not ideal, but better than nothing
      (pmap (partial build-image image-tiles)
            (top-left-tiles image-tiles))))))
