(ns advent-of-code-2020.day01
  (:require
   [clojure.string :as string]))

(defn- combinations
  "Creates all possible combinations of collection prefix and elements in input."
  [prefix input]
  (loop [[y & ys :as input] input
         res []]
    (if (seq input)
      (recur ys (concat res (mapv conj (repeat (conj prefix y)) ys)))
      res)))

(defonce ^:private expenses
  (map (fn [x] (Integer/parseInt x))
       (string/split-lines
        (slurp "resources/expense_report.txt"))))

(defn- day1
  "https://adventofcode.com/2020/day/1"
  [combinator filter]
  (fn day1-fn
    ([]
     (day1-fn expenses))
    ([expenses]
     (->> expenses
          (combinator)
          (filter)
          (first)
          (apply *)))))

(def day1-1 (day1 (partial combinations [])
                  (partial filter (fn [[a b]] (= (+ a b) 2020)))))

(defn- expense-3-combinations
  "Creates all possible combinations of 3 expense entries"
  [input]
  (loop [[x & xs :as input] input
         res []]
    (if (seq input)
      (recur xs (concat res (combinations [x] xs)))
      res)))

(def day1-2 (day1 expense-3-combinations
                  (partial filter (fn [[a b c]] (= (+ a b c) 2020)))))