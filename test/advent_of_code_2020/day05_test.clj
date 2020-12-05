(ns advent-of-code-2020.day05-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day05 :refer :all]))

(deftest day5-1-example-boarding-pass->seat-1-test
  (testing "boarding-pass->seat FBFBBFFRLR"
    (is (= [44 5]
           (#'advent-of-code-2020.day05/boarding-pass->seat "FBFBBFFRLR")))))

(deftest day5-1-example-boarding-pass->seat-2-test
  (testing "boarding-pass->seat BFFFBBFRRR"
    (is (= [70 7]
           (#'advent-of-code-2020.day05/boarding-pass->seat "BFFFBBFRRR")))))

(deftest day5-1-example-boarding-pass->seat-3-test
  (testing "boarding-pass->seat FFFBBBFRRR"
    (is (= [14 7]
           (#'advent-of-code-2020.day05/boarding-pass->seat "FFFBBBFRRR")))))

(deftest day5-1-example-boarding-pass->seat-4-test
  (testing "boarding-pass->seat BBFFBBFRLL"
    (is (= [102 4]
           (#'advent-of-code-2020.day05/boarding-pass->seat "BBFFBBFRLL")))))

(deftest day5-1-example-seat->seat-id-1-test
  (testing "seat->seat-id [44 5]"
    (is (= 357 (#'advent-of-code-2020.day05/seat->seat-id [44 5])))))

(deftest day5-1-example-seat->seat-id-2-test
  (testing "seat->seat-id [70 7]"
    (is (= 567 (#'advent-of-code-2020.day05/seat->seat-id [70 7])))))

(deftest day5-1-example-seat->seat-id-3-test
  (testing "seat->seat-id [14 7]"
    (is (= 119 (#'advent-of-code-2020.day05/seat->seat-id [14 7])))))

(deftest day5-1-example-seat->seat-id-4-test
  (testing "seat->seat-id [102 4]"
    (is (= 820 (#'advent-of-code-2020.day05/seat->seat-id [102 4])))))

(deftest day5-1-test
  (testing "Day5 - Part 1"
    (is (= 928 (day5-1)))))

(deftest day5-2-test
  (testing "Day5 - Part 1"
    (is (= 610 (day5-2)))))