(ns advent-of-code-2020.day09-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day09 :refer :all]))

(deftest day9-1-example-1-test
  (testing "Day 9 - Part 1 - Example 1 - invalid-xmas"
    (is (nil?
         (#'advent-of-code-2020.day09/invalid-xmas 25 (range 1 27))))))

(deftest day9-1-example-2-test
  (testing "Day 9 - Part 1 - Example 2 - invalid-xmas"
    (is (nil?
         (#'advent-of-code-2020.day09/invalid-xmas 25 (concat (range 1 26)
                                                              [49]))))))

(deftest day9-1-example-3-test
  (testing "Day 9 - Part 1 - Example 3 - invalid-xmas"
    (is (= 100
           (#'advent-of-code-2020.day09/invalid-xmas 25 (concat (range 1 26)
                                                                [100]))))))

(deftest day9-1-example-4-test
  (testing "Day 9 - Part 1 - Example 4 - invalid-xmas"
    (is (= 50
           (#'advent-of-code-2020.day09/invalid-xmas 25 (concat (range 1 26)
                                                                [50]))))))

(defonce ^:private longer-example
  [35 20 15 25 47 40 62 55 65 95 102 117 150 182 127 219 299 277 309 576])

(deftest day9-1-example-5-test
  (testing "Day 9 - Part 1 - Example 5 - invalid-xmas"
    (is (= 127 (#'advent-of-code-2020.day09/invalid-xmas 5 longer-example)))))

(deftest day9-1-test
  (testing "Day 9 - Part 1"
    (is (= 552655238 (day9-1)))))

(deftest day9-2-example-test
  (testing "Day 9 - Part 2 - Example 5"
    (is (= 62 (day9-2 5 longer-example)))))

(deftest day9-2-test
  (testing "Day 9 - Part 2"
    (is (= 70672245 (day9-2)))))