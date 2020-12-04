(ns advent-of-code-2020.core
  (:require
   [clojure.string :as string]))

;; Day 1

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
  []
  (let [expenses (read-string (str "[" (slurp "resources/expense_report.txt") "]"))]
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
  []
  (let [expenses (read-string (str "[" (slurp "resources/expense_report.txt") "]"))]
    (->> expenses
         expense-3-combinations
         (filter (fn [[a b c]] (= (+ a b c) 2020)))
         first
         (apply *))))

;; Day 2

(defn- parse-password+policy [[a b letter password]]
  {:a (read-string a)
   :b (read-string b)
   :letter (first letter) ; String of length 1 to char
   :password password})

(defn- parse-password+policies [password-policies]
  (map (fn [password-policy] (parse-password+policy
                              (string/split password-policy #"\s+|:\s+|-")))
       password-policies))

(defn- policy-fulfiled-1? [{:keys [a b letter password]}]
  (<= a (get (frequencies password) letter 0) b))

(defn day2-1
  "https://adventofcode.com/2020/day/2"
  []
  (let [password-policies (string/split-lines (slurp "resources/passwords.txt"))
        p (parse-password+policies password-policies)]
    (count (filter policy-fulfiled-1? p))))

(defn- policy-fulfiled-2? [{:keys [a b letter password]}]
  (cond
    (< (count password) (dec b)) false
    (= (get password (dec a)) letter) (not (= (get password (dec b)) letter))
    :else (= (get password (dec b)) letter)))

(defn day2-2
  "https://adventofcode.com/2020/day/2"
  []
  (let [password-policies (string/split-lines (slurp "resources/passwords.txt"))
        p (parse-password+policies password-policies)]
    (count (filter policy-fulfiled-2? p))))

;; Day 3