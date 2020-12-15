(ns advent-of-code-2020.day15-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day15 :refer :all]))

(defonce ^:private example-input [0 3 6])

(deftest day15-1-example-1-fill-numbers-test
  (testing "Day 15 - Part 1 - Example 1 - game-setup"
    (is (= {0 1, 3 2, 6 3}
           (#'advent-of-code-2020.day15/game-setup
            (conj example-input :dummy))))))

(deftest day15-1-example-1-next-number-0-test
  (testing "Day 15 - Part 1 - Example 1 - game-setup 0"
    (is (= [{0 4, 3 2, 6 3} 3]
           (#'advent-of-code-2020.day15/next-number
            (#'advent-of-code-2020.day15/game-setup
             (conj example-input :dummy))
            4 0)))))

(deftest day15-1-example-1-next-number-2-test
  (testing "Day 15 - Part 1 - Example 1 - game-setup 2"
    (is (= [{0 1, 3 4, 6 3} 2]
           (#'advent-of-code-2020.day15/next-number
            (#'advent-of-code-2020.day15/game-setup
             (conj example-input :dummy))
            4 3)))))

(deftest day15-1-example-1-next-number-6-test
  (testing "Day 15 - Part 1 - Example 1 - game-setup 6"
    (is (= [{0 1, 3 2, 6 4} 1]
           (#'advent-of-code-2020.day15/next-number
            (#'advent-of-code-2020.day15/game-setup
             (conj example-input :dummy))
            4 6)))))

(deftest day15-1-example-1-next-number-12-test
  (testing "Day 15 - Part 1 - Example 1 - game-setup 6"
    (is (= [{0 1, 3 2, 6 3, 12 4} 0]
           (#'advent-of-code-2020.day15/next-number
            (#'advent-of-code-2020.day15/game-setup
             (conj example-input :dummy))
            4 12)))))

(deftest day15-1-example-1-turn-4-test
  (testing "Day 15 - Part 1 - Example 1 - turn 4"
    (is (= 0 (day15-1 example-input 4)))))

(deftest day15-1-example-1-turn-5-test
  (testing "Day 15 - Part 1 - Example 1 - turn 5"
    (is (= 3 (day15-1 example-input 5)))))

(deftest day15-1-example-1-turn-6-test
  (testing "Day 15 - Part 1 - Example 1 - turn 6"
    (is (= 3 (day15-1 example-input 6)))))

(deftest day15-1-example-1-turn-7-test
  (testing "Day 15 - Part 1 - Example 1 - turn 7"
    (is (= 1 (day15-1 example-input 7)))))

(deftest day15-1-example-1-turn-8-test
  (testing "Day 15 - Part 1 - Example 1 - turn 8"
    (is (= 0 (day15-1 example-input 8)))))

(deftest day15-1-example-1-turn-9-test
  (testing "Day 15 - Part 1 - Example 1 - turn 9"
    (is (= 4 (day15-1 example-input 9)))))

(deftest day15-1-example-1-turn-10-test
  (testing "Day 15 - Part 1 - Example 1 - turn 10"
    (is (= 0 (day15-1 example-input 10)))))

(deftest day15-1-example-2-test
  (testing "Day 15 - Part 1 - Example 2"
    (is (= 1 (day15-1 [1 3 2] 2020)))))

(deftest day15-1-example-3-test
  (testing "Day 15 - Part 1 - Example 3"
    (is (= 10 (day15-1 [2 1 3] 2020)))))

(deftest day15-1-example-4-test
  (testing "Day 15 - Part 1 - Example 4"
    (is (= 27 (day15-1 [1 2 3] 2020)))))

(deftest day15-1-example-5-test
  (testing "Day 15 - Part 1 - Example 5"
    (is (= 78 (day15-1 [2 3 1] 2020)))))

(deftest day15-1-example-6-test
  (testing "Day 15 - Part 1 - Example 6"
    (is (= 438 (day15-1 [3 2 1] 2020)))))

(deftest day15-1-example-7-test
  (testing "Day 15 - Part 1 - Example 6"
    (is (= 1836 (day15-1 [3 1 2] 2020)))))

(deftest day15-1-test
  (testing "Day 15 - Part 1"
    (is (= 1428 (day15-1)))))

(deftest day15-2-example-1-test
  (testing "Day 15 - Part 2 - Example 1"
    (is (= 175594 (day15-2 example-input 30000000)))))

(deftest day15-2-example-2-test
  (testing "Day 15 - Part 2 - Example 2"
    (is (= 2578 (day15-2 [1 3 2] 30000000)))))

(deftest day15-2-example-3-test
  (testing "Day 15 - Part 2 - Example 3"
    (is (= 3544142 (day15-2 [2 1 3] 30000000)))))

(deftest day15-2-example-4-test
  (testing "Day 15 - Part 2 - Example 4"
    (is (= 261214 (day15-2 [1 2 3] 30000000)))))

(deftest day15-2-example-5-test
  (testing "Day 15 - Part 2 - Example 5"
    (is (= 6895259 (day15-2 [2 3 1] 30000000)))))

(deftest day15-2-example-6-test
  (testing "Day 15 - Part 2 - Example 6"
    (is (= 18 (day15-2 [3 2 1] 30000000)))))

(deftest day15-2-example-7-test
  (testing "Day 15 - Part 2 - Example 6"
    (is (= 362 (day15-2 [3 1 2] 30000000)))))

(deftest day15-2-test
  (testing "Day 15 - Part 2"
    (is (= 3718541 (day15-2 30000000)))))