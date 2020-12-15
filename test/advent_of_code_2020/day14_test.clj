(ns advent-of-code-2020.day14-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day14 :refer :all]))

(defonce ^:private example-input
  "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0")

(deftest day14-1-example-1-read-programm-test
  (testing "Day 14 - Part 1 - Example 1 - read-program"
    (is (= [[:mask [64 68719476733]]
            [:assign 8 11]
            [:assign 7 101]
            [:assign 8 0]]
           (into []
                 (#'advent-of-code-2020.day14/read-program 1 example-input))))))

(deftest day14-1-example-1-test
  (testing "Day 14 - Part 1 - Example 1"
    (is (= 165 (day14-1 (#'advent-of-code-2020.day14/read-program 1
                                                                  example-input))))))

(deftest day14-1-test
  (testing "Day 14 - Part 1"
    (is (= 14839536808842 (day14-1)))))

(deftest day14-2-generate-masks-test
  (testing "Day 14 - Part 2 - Generate masks - empty"
    (is (= [0 0 [0]] (#'advent-of-code-2020.day14/generate-masks [])))))

(deftest day14-2-generate-masks-2-test
  (testing "Day 14 - Part 2 - Generate masks - [X]"
    (is (= [0 0 [0 1]]
           (#'advent-of-code-2020.day14/generate-masks [\X])))))

(deftest day14-2-generate-masks-3-test
  (testing "Day 14 - Part 2 - Generate masks - [XX]"
    (is (= [0 0 [0 2 1 3]]
           (#'advent-of-code-2020.day14/generate-masks [\X \X])))))

(deftest day14-2-generate-masks-4-test
  (testing "Day 14 - Part 2 - Generate masks - [0]"
    (is (= [0 1 [0]] (#'advent-of-code-2020.day14/generate-masks [\0])))))

(deftest day14-2-generate-masks-5-test
  (testing "Day 14 - Part 2 - Generate masks - [1]"
    (is (= [1 1 [1]] (#'advent-of-code-2020.day14/generate-masks [\1])))))

(deftest day14-2-generate-masks-6-test
  (testing "Day 14 - Part 2 - Generate masks - [1 X]"
    (is (= [2 2 [2 3]] (#'advent-of-code-2020.day14/generate-masks [\1 \X])))))

(defonce ^:private example-input-2
  "mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1")

(deftest day14-2-example-2-test
  (testing "Day 14 - Part 2 - Example 2"
    (is (= 208
           (day14-2 (#'advent-of-code-2020.day14/read-program 2
                                                              example-input-2))))))

(deftest day14-2-test
  (testing "Day 14 - Part 2"
    (is (= 4215284199669 (day14-2)))))