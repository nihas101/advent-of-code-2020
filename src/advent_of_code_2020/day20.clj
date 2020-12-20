(ns advent-of-code-2020.day20
  (:require
   [clojure.string :as string]))

(defn- read-tile-id [tile-id]
  (Long/parseLong (re-find #"\d+" tile-id)))

(defn- read-image [image]
  (let [width (count (first image))
        height (count image)]
    (-> (reduce (fn [im [y image-row]]
                  (reduce (fn [im [x pixel]]
                            (if (= \# pixel)
                              (assoc im [x y] 1)
                              im))
                          im (mapv vector (range) image-row)))
                {}
                (mapv vector (range) image))
        (assoc ,,, :width width)
        (assoc ,,, :height height))))

(defn- read-tile [tile]
  (let [[tile-id & image] (string/split-lines tile)]
    {:tile-id (read-tile-id tile-id)
     :image (read-image image)}))

(defn- read-input [s]
  (mapv read-tile (string/split s #"(\r?\n){2}")))

(defn- serialize-edge [edge]
  [(read-string (string/join (conj edge "2r"))) ; Ordinary value
   (read-string (string/join (conj (reverse edge) "2r")))]) ; Flipped value

(defn- serialize-edges [{:keys [width height] :as image}]
  (let [north (map (fn [x y] (get image [x y] 0))
                   (range width) (repeat 0))
        east (map (fn [x y] (get image [x y] 0))
                  (repeat (dec width)) (range height))
        south (map (fn [x y] (get image [x y] 0))
                   (range width) (repeat (dec height)))
        west (map (fn [x y] (get image [x y] 0))
                  (repeat 0) (range height))]
    {:north (serialize-edge north)
     :east (serialize-edge east)
     :south (serialize-edge south)
     :west (serialize-edge west)
     :flipped false}))

(defn- rotate-edges [edges]
  (let [old-north (:north edges)]
    (-> edges
        (assoc ,,, :north (:west edges))
        (assoc ,,, :west (:south edges))
        (assoc ,,, :south (:east edges))
        (assoc ,,, :east old-north))))

(defn- flip-edges [edges]
  (update edges :flipped not))

(defn- get-edges [{:keys [flipped] :as edges}]
  (let [es ((juxt :north :east :south :west) edges)]
    (if flipped (mapv second es) (mapv first es))))

(defn- collect-edges [image-tiles]
  (mapcat get-edges
          (concat (mapv :image image-tiles)
                  (mapv #(flip-edges (:image %)) image-tiles))))

(defn- find-corners [image-tiles]
  (loop [[corner-candidate & corner-candidates] image-tiles
         corners []]
    (let [other-edges (collect-edges (remove #{corner-candidate} image-tiles))]
      (cond
        ;; We are done
        (nil? corner-candidate) corners
        ;; We found a corner piece
        (= 2 (count (remove (set other-edges) (get-edges (:image corner-candidate)))))
        (recur corner-candidates (conj corners corner-candidate))
        ;; We found a tile thats not a corner piece
        :else (recur corner-candidates corners)))))

(defn day20-1
  ([] (day20-1 (read-input (slurp "resources/image_tiles.txt"))))
  ([image-tiles]
   (transduce
    (map :tile-id)
    *
    (find-corners (mapv #(update % :image serialize-edges) image-tiles)))))