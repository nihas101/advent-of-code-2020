(ns advent-of-code-2020.core-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2020.core :refer :all]))

(deftest day1-1-test
  (testing "Day 1 - Part 1"
    (is (= 1013211 (day1-1)))))

(deftest day1-2-test
  (testing "Day 1 - Part 2"
    (is (= 13891280 (day1-2)))))