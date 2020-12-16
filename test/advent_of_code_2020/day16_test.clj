(ns advent-of-code-2020.day16-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day16 :refer :all]))

(defonce ^:private example-input
  "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12")

(deftest day16-1-example-1-read-input-test
  (testing "Day 16 - Part 1 - Example 1 - read-input"
    (is (= {:my-ticket [7 1 14]
            :other-tickets [[7 3 47]
                            [40 4 50]
                            [55 2 20]
                            [38 6 12]]
            :rules [["class" [1 3] [5 7]]
                    ["row" [6 11] [33 44]]
                    ["seat" [13 40] [45 50]]]}
           (#'advent-of-code-2020.day16/read-input example-input)))))

(deftest day16-1-example-1-test
  (testing "Day 16 - Part 1 - Example 1"
    (is (= 71 (day16-1 (#'advent-of-code-2020.day16/read-input example-input))))))

(deftest day16-1-test
  (testing "Day 16 - Part 1 - Example 1"
    (is (= 27802 (day16-1)))))

(deftest day16-2-test
  (testing "Day 16 - Part 2"
    (is (= 279139880759 (day16-2)))))