(ns advent-of-code-2020.day25-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day25 :refer :all]))

(deftest day25-1-example-loop-size-card-test
  (testing "Day 25 - Part 1 - loop-size card"
    (is (= 8
           (#'advent-of-code-2020.day25/loop-size 5764801 7)))))

(deftest day25-1-example-loop-size-door-test
  (testing "Day 25 - Part 1 - loop-size card"
    (is (= 11
           (#'advent-of-code-2020.day25/loop-size 17807724 7)))))

(deftest day25-1-example-encryption-key-test
  (testing "Day 25 - Part 1 - encryption-key"
    (is (= 14897079
           (#'advent-of-code-2020.day25/encryption-key 5764801 11)
           (#'advent-of-code-2020.day25/encryption-key 17807724 8)))))

(deftest day25-1-test
  (testing "Day 25 - Part 1"
    (is (= [7269858 7269858] (day25-1)))))