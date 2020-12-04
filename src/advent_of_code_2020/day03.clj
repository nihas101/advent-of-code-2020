(ns advent-of-code-2020.day03
  (:require
   [clojure.string :as string]))

(defn- indices-of
  "Returns all indices on which `value` appears in `s`"
  [s value]
  (loop [curr 0
         idxs #{}]
    (if-let [idx (string/index-of s value curr)]
      (recur (inc idx) (conj idxs idx))
      idxs)))

(defn- tree-map
  "Creates a map of the tree from `lines`"
  [lines]
  (loop [trees {:height (count lines)
                :width (count (first lines))}
         idx 0
         [l & ls :as lines] lines]
    (if (seq lines)
      (recur
       (assoc trees idx (indices-of l \#))
       (inc idx)
       ls)
      trees)))

(defn- parse-map
  "Parses the given `map` of trees"
  [map]
  (let [lines (string/split-lines map)]
    (tree-map lines)))

(defn- next-step
  "Creates the next step from a delta and a previous one"
  [step-x step-y [x y]]
  [(+ x step-x) (+ y step-y)])

(defn- follow-slope
  "Returns all positions in the slope created by going right `r` and `down `d`
   times on the given map."
  [{:keys [height width]} r d]
  (mapv (fn [[x y]] [(mod x width) y])
        (take-while (fn [[_ y]] (= y (mod y height)))
                    (iterate (partial next-step r d) [r d]))))

(defn day3-1
  "https://adventofcode.com/2020/day/3"
  ([r d]
   (day3-1 (parse-map (slurp "resources/tree_map.txt")) r d))
  ([tree-map r d]
   (let [slope (follow-slope tree-map r d)]
     (count (filter (fn [[x y]] (get-in tree-map [y x])) slope)))))

(defn day3-2
  "https://adventofcode.com/2020/day/3"
  ([]
   (day3-2 (parse-map (slurp "resources/tree_map.txt"))))
  ([tree-map]
   (reduce *
           (map (fn [[r d]] (day3-1 tree-map r d))
                [[1 1] [3 1] [5 1] [7 1] [1 2]]))))