(ns advent-of-code-2020.day01
  (:require
   [advent-of-code-2020.utils :as u]))

(defn- combinations
  "Creates all possible combinations of collection prefix and elements in input."
  [prefix input]
  (loop [[y & ys :as input] input
         res []]
    (if (seq input)
      (recur ys (concat res (mapv conj (repeat (conj prefix y)) ys)))
      res)))

(defonce ^:private expenses
  (u/read-longs (slurp "resources/expense_report.txt") u/line-endings))

(defn- day1
  "https://adventofcode.com/2020/day/1"
  ([combinator filter]
   (day1 combinator filter expenses))
  ([combinator filter expenses]
   (->> expenses
        (combinator)
        (filter)
        (first)
        (apply *))))

(def day1-1 (partial day1 (partial combinations [])
                     (partial filter (fn [[^long a ^long b]]
                                       (= (+ a b) 2020)))))

(defn- expense-3-combinations
  "Creates all possible combinations of 3 expense entries"
  [input]
  (loop [[x & xs :as input] input
         res []]
    (if (seq input)
      (recur xs (concat res (combinations [x] xs)))
      res)))

(def day1-2 (partial day1 expense-3-combinations
                     (partial filter (fn [[^long a ^long b ^long c]]
                                       (= (+ a b c) 2020)))))