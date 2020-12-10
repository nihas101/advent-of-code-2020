(ns advent-of-code-2020.day10-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day10 :refer :all]))

(defonce ^:private adapters-1 [16 10 15 5 1 11 7 19 6 12 4])

(defonce ^:private adapters-2 [28 33 18 42 31 14 46 20 48 47 24 23 49 45 19
                               38 39 11 1 32 25 35 8 17 7 9 4 2 34 10 3])

(deftest day10-1-example-1-test
  (testing "Day 10 - Part 1 - Example 1"
    (is (= 35 (day10-1 adapters-1)))))

(deftest day10-1-example-2-test
  (testing "Day 10 - Part 1 - Example 2"
    (is (= 220 (day10-1 adapters-2)))))

(deftest day10-1-test
  (testing "Day 10 - Part 1"
    (is (= 1656 (day10-1)))))

(deftest day10-2-example-1-test
  (testing "Day 10 - Part 2 - Example 1"
    (is (= 8 (day10-2 adapters-1)))))

(deftest day10-2-example-2-test
  (testing "Day 10 - Part 2 - Example 2"
    (is (= 19208 (day10-2 adapters-2)))))

(deftest day10-2-example-3-test
  (testing "Day 10 - Part 2 - Example 3"
    (is (= 1 (day10-2 [])))))

(deftest day10-2-example-4-test
  (testing "Day 10 - Part 2 - Example 4"
    (is (= 2 (day10-2 [1 2])))))

(deftest day10-2-example-5-test
  (testing "Day 10 - Part 2 - Example 5"
    (is (= 4 (day10-2 [1 2 3])))))

(deftest day10-2-example-6-test
  (testing "Day 10 - Part 2 - Example 6"
    (is (= 6 (day10-2 [1 1 1])))))

(deftest day10-2-test
  (testing "Day 10 - Part 2"
    (is (= 56693912375296 (day10-2)))))