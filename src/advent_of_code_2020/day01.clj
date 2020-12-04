(ns advent-of-code-2020.day01)

(defn- combinations
  "Creates all possible combinations of collection prefix and elements in input."
  [prefix input]
  (loop [[y & ys :as input] input
         res []]
    (if (seq input)
      (recur ys (concat res (mapv conj (repeat (conj prefix y)) ys)))
      res)))

(defn day1-1
  "https://adventofcode.com/2020/day/1"
  ([]
   (let [expenses (read-string (str "[" (slurp "resources/expense_report.txt") "]"))]
     (day1-1 expenses)))
  ([expenses]
   (->> expenses
        (combinations [])
        (filter (fn [[a b]] (= (+ a b) 2020)))
        first
        (apply *))))

(defn- expense-3-combinations
  "Creates all possible combinations of 3 expense entries"
  [input]
  (loop [[x & xs :as input] input
         res []]
    (if (seq input)
      (recur xs (concat res (combinations [x] xs)))
      res)))

(defn day1-2
  "https://adventofcode.com/2020/day/1"
  ([]
   (let [expenses (read-string (str "[" (slurp "resources/expense_report.txt") "]"))]
     (day1-2 expenses)))
  ([expenses]
   (->> expenses
        expense-3-combinations
        (filter (fn [[a b c]] (= (+ a b c) 2020)))
        first
        (apply *))))