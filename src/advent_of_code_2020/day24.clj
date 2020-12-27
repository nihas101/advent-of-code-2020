(ns advent-of-code-2020.day24
  (:require
   [clojure.string :as string]
   [advent-of-code-2020.utils :as u]))

(defn- read-pos [pos]
  (if (empty? pos)
    [0 0]
    (reduce (fn [[^long x ^long y] d]
              (case d
                "nw" [(dec x) (inc y)]
                "ne" [(inc x) (inc y)]
                "sw" [(dec x) (dec y)]
                "se" [(inc x) (dec y)]
                "w" [(- x 2) y]
                "e" [(+ x 2) y]))
            [0 0] (re-seq #"nw|ne|sw|se|w|e" pos))))

(defn- read-input [s]
  (mapv read-pos (string/split s u/line-endings)))

(defn- flip-tiles [tiles]
  (reduce (fn [black-tiles tile]
            (if (contains? black-tiles tile)
              (disj black-tiles tile)
              (conj black-tiles tile)))
          #{} tiles))

(defonce ^:private tiles
  (read-input (slurp "resources/tiles.txt")))

(defn day24-1
  ([] (day24-1 tiles))
  ([tiles]
   (count (flip-tiles tiles))))

(defn- neighbors [[^long x ^long y]]
  (for [dy [1 0 -1]
        dx (if (zero? ^long dy) [-2 2] [-1 1])]
    [(+ x ^long dx) (+ y ^long dy)]))

(defn- black-neighboring-tiles [black-tiles tile]
  (count (filter black-tiles (neighbors tile))))

(defn- handle-black-tile! [new-tiles black-tiles tile]
  (let [black-neighbor-tiles (black-neighboring-tiles black-tiles tile)]
    (if (or (zero? ^long black-neighbor-tiles) (< 2 ^long black-neighbor-tiles))
      new-tiles
      (conj! new-tiles tile)))) ; The tiles stays on the black side

(defn- handle-white-tile! [new-tiles black-tiles tile]
  (if (= 2 ^long (black-neighboring-tiles black-tiles tile))
    (conj! new-tiles tile) ; The tile is flipped to the black side
    new-tiles))

(defn- next-day [black-tiles]
  (let [interesting-tiles (persistent! (reduce conj! (transient black-tiles)
                                               (mapcat neighbors black-tiles)))]
    (persistent!
     (reduce (fn [new-tiles tile]
               (if (contains? black-tiles tile)
                 (handle-black-tile! new-tiles black-tiles tile)
                 (handle-white-tile! new-tiles black-tiles tile)))
             (transient #{})
             interesting-tiles))))

(defn day24-2
  ([] (day24-2 tiles))
  ([tiles] (day24-2 tiles 100))
  ([tiles ^long day]
   (loop [tiles (flip-tiles tiles)
          day day]
     (if (zero? day)
       (count tiles)
       (recur (next-day tiles)
              (dec day))))))