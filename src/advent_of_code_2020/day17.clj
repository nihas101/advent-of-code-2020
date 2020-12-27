(ns advent-of-code-2020.day17
  (:require
   [clojure.string :as string]))

(defonce ^:private cubes #{})

(defn- read-cube-line [^long dims cubes [y line]]
  (reduce (fn [c [x a]] (if (= \# a) (conj c (reduce conj [x y] (repeat (- dims 2) 0))) c))
          cubes (mapv vector (range) line)))

(defn- read-input [dims i]
  (reduce (partial read-cube-line dims)
          cubes
          (mapv vector (range) (string/split-lines i))))

(defn- expand-to-cube [p]
  (loop [[d & ds :as dims] (range (count p))
         ps [p]]
    (if (seq dims)
      (recur ds
             (concat ps
                     (mapv (fn [p] (update p d - 1)) ps)
                     (mapv (fn [p] (update p d + 1)) ps)))
      ps)))

(defn- considerable-pos [cubes]
  (transduce
   (mapcat expand-to-cube)
   conj #{} cubes))

(defn- active-neighbors [cubes pos]
  (filter (partial contains? cubes)
          ;; Remove the original position
          (rest (expand-to-cube pos))))

(defn- change-state [old-cubes new-cubes cube]
  (let [active (contains? old-cubes cube)]
    (cond
      ;; The cube stays active
      (and active (contains? #{2 3} (count (active-neighbors old-cubes cube))))
      new-cubes
      ;; An inactive cube becomes active
      (and (not active) (= 3 (count (active-neighbors old-cubes cube))))
      (conj new-cubes cube)
      ;; Otherwise the cube becomes inactive
      :else (disj new-cubes cube))))

(defn- game-of-cubes-cycle [new-cubes]
  (reduce (partial change-state new-cubes) new-cubes
          (considerable-pos new-cubes)))

(defonce ^:private init-cubes ".###..#.
##.##...
....#.#.
#..#.###
...#...#
##.#...#
#..##.##
#.......")

(defn- day17-fn [dims cycles]
  (fn day17
    ([] (day17 (read-input dims init-cubes) cycles))
    ([init-cubes cycles]
     (count (nth (iterate game-of-cubes-cycle init-cubes) cycles)))))

(def day17-1 (day17-fn 3 6))

(def day17-2 (day17-fn 4 6))