(ns advent-of-code-2020.day05
  (:require
   [clojure.string :as string]))

(defn- lower-half [a b]
  [a (Math/floor (/ (+ a b) 2))])

(defn- upper-half [a b]
  [(Math/ceil (/ (+ a b) 2)) b])

(defn- boarding-pass->seat [boarding-pass]
  (loop [[c-low c-up :as column] [0 127]
         [r-low r-up :as row] [0 7]
         [b & bs :as boarding-pass] boarding-pass]
    (cond
      (empty? boarding-pass) [(int c-low) (int r-low)]
      (= b \F) (recur (lower-half c-low c-up) row bs)
      (= b \B) (recur (upper-half c-low c-up) row bs)
      (= b \R) (recur column (upper-half r-low r-up) bs)
      (= b \L) (recur column (lower-half r-low r-up) bs))))

(defn- seat->seat-id [[row column]]
  (+ (* row 8) column))

(defonce ^:private boarding-pass->seat-id
  (comp
   (map boarding-pass->seat)
   (map seat->seat-id)))

(defonce ^:private boarding-passes
  (string/split-lines (slurp "resources/boarding_passes.txt")))

(defn day5-1
  "https://adventofcode.com/2020/day/5"
  ([] (day5-1 boarding-passes))
  ([boarding-passes]
   (reduce max
           (transduce boarding-pass->seat-id conj boarding-passes))))

(defn- remove-neighbouring [rem c]
  (loop [[a & as :as c] c
         res []]
    (cond
      (empty? c) res
      (contains? rem a) (recur as res)
      ;; Neighbouring seats are taken
      (and (contains? rem (inc a)) (contains? rem (dec a))) (recur as (conj res a))
      :else (recur as res))))

(defn day5-2
  "https://adventofcode.com/2020/day/5"
  ([] (day5-2 boarding-passes))
  ([boarding-passes]
   (first
    (remove-neighbouring
     (transduce boarding-pass->seat-id conj #{} boarding-passes)
     (range 0 928)))))