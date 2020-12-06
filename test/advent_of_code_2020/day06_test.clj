(ns advent-of-code-2020.day06-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day06 :refer :all]))

(deftest day6-1-example-test
  (testing "Day 6 - Part 1 - Example"
    (is (= 11 (day6-1 [["abc"]
                       ["a" "b" "c"]
                       ["ab" "ac"]
                       ["a" "a" "a" "a"]
                       ["b"]])))))

(deftest day6-1-test
  (testing "Day 6 - Part 1"
    (is (= 6161 (day6-1)))))

(deftest day6-2-test
  (testing "Day 6 - Part 2"
    (is (= 2971 (day6-2)))))