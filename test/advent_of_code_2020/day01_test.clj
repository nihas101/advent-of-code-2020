(ns advent-of-code-2020.day01-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day01 :refer :all]))

(deftest day1-1-example-test
  (testing "Day 1 - Part 1 - Example"
    (is (= 514579 (day1-1 [1721
                           979
                           366
                           299
                           675
                           1456])))))

(deftest day1-1-test
  (testing "Day 1 - Part 1"
    (is (= 1013211 (day1-1)))))

(deftest day1-2-example-test
  (testing "Day 1 - Part 2 - Example"
    (is (= 241861950 (day1-2 [1721
                              979
                              366
                              299
                              675
                              1456])))))

(deftest day1-2-test
  (testing "Day 1 - Part 2"
    (is (= 13891280 (day1-2)))))