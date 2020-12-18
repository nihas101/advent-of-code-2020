(ns advent-of-code-2020.day18-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day18 :refer :all]))

(defonce ^:private example-input "1 + 2 * 3 + 4 * 5 + 6")
(defonce ^:private example-input-2 "1 + (2 * 3) + (4 * (5 + 6))")
(defonce ^:private example-input-3 "2 * 3 + (4 * 5)")
(defonce ^:private example-input-4 "5 + (8 * 3 + 9 + 3 * 4 * 3)")
(defonce ^:private example-input-5 "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
(defonce ^:private example-input-6 "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")

(deftest day18-1-examples-test
  (testing "Day 18 - Part 1 - Examples"
    (are [x y] (= x (eval y))
      71 (day18-1 [example-input])
      51 (day18-1 [example-input-2])
      26 (day18-1 [example-input-3])
      437 (day18-1 [example-input-4])
      12240 (day18-1 [example-input-5])
      13632 (day18-1 [example-input-6]))))

(deftest day18-1-test
  (testing "Day 18 - Part 1"
    (is (= 8298263963837 (day18-1)))))

(deftest day18-2-examples-test
  (testing "Day 18 - Part 2 - Examples"
    (are [x y] (= x (eval y))
      231 (day18-2 [example-input])
      51 (day18-2 [example-input-2])
      46 (day18-2 [example-input-3])
      1445 (day18-2 [example-input-4])
      669060 (day18-2 [example-input-5])
      23340 (day18-2 [example-input-6]))))

(deftest day18-2-test
  (testing "Day 18 - Part 2"
    (is (= 145575710203332 (day18-2)))))