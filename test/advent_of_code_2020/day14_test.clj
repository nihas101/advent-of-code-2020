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
    (is (= [[:mask (reverse "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")]
            [:assign 8 (reverse "1011")]
            [:assign 7 (reverse "1100101")]
            [:assign 8 (reverse "0")]]
           (#'advent-of-code-2020.day14/read-program example-input)))))

(deftest day14-1-example-1-test
  (testing "Day 14 - Part 1 - Example 1"
    (is (= 165 (day14-1
                (#'advent-of-code-2020.day14/read-program example-input))))))

(deftest day14-1-test
  (testing "Day 14 - Part 1"
    (is (= 14839536808842 (day14-1)))))

(deftest day14-2-generate-sinks-test
  (testing "Day 14 - Part 2 - Generate sinks - empty"
    (is (= [nil] (#'advent-of-code-2020.day14/generate-sinks 0 [])))))

(deftest day14-2-generate-sinks-2-test
  (testing "Day 14 - Part 2 - Generate sinks - [X]"
    (is (= [0 1]
           (#'advent-of-code-2020.day14/generate-sinks 0 [\X])))))

(deftest day14-2-generate-sinks-3-test
  (testing "Day 14 - Part 2 - Generate sinks - [XX]"
    (is (= [0 1 2 3]
           (#'advent-of-code-2020.day14/generate-sinks 0 [\X \X])))))

(deftest day14-2-generate-sinks-4-test
  (testing "Day 14 - Part 2 - Generate sinks - [0]"
    (is (= [1] (#'advent-of-code-2020.day14/generate-sinks 1 [\0])))))

(deftest day14-2-generate-sinks-5-test
  (testing "Day 14 - Part 2 - Generate sinks - [1]"
    (is (= [1] (#'advent-of-code-2020.day14/generate-sinks 0 [\1])))))

(deftest day14-2-generate-sinks-6-test
  (testing "Day 14 - Part 2 - Generate sinks - [1 X]"
    (is (= [1 3] (#'advent-of-code-2020.day14/generate-sinks 2 [\1 \X])))))

(defonce ^:private example-input-2
  "mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1")

(deftest day14-2-example-2-test
  (testing "Day 14 - Part 2 - Example 2"
    (is (= 208
           (day14-2
            (#'advent-of-code-2020.day14/read-program example-input-2))))))

(deftest day14-2-test
  (testing "Day 14 - Part 2"
    (is (= 4215284199669 (day14-2)))))