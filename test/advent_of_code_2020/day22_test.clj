(ns advent-of-code-2020.day22-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day22 :refer :all]))

(defonce ^:private example-input
  "Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10")

(deftest day22-1-example-read-input-test
  (testing "Day 22 - Part 1 - example - read-input"
    (is (= {1 [9 2 6 3 1]
            2 [5 8 4 7 10]}
           (#'advent-of-code-2020.day22/read-input example-input)))))

(deftest day22-1-example-play-round-test
  (testing "Day 22 - Part 1 - example - play-round"
    (is (= {1 [2 6 3 1 9 5]
            2 [8 4 7 10]
            :winner 1}
           (#'advent-of-code-2020.day22/play-round
            (#'advent-of-code-2020.day22/read-input example-input))))))

(deftest day22-1-example-test
  (testing "Day 22 - Part 1 - example"
    (is (= 306
           (day22-1
            (#'advent-of-code-2020.day22/read-input example-input))))))

(deftest day22-1-test
  (testing "Day 22 - Part 1"
    (is (= 35370 (day22-1)))))

(defonce ^:private example-input-2
  "Player 1:
43
19

Player 2:
2
29
14")

(deftest day22-1-example-2-read-input-test
  (testing "Day 22 - Part 2 - example 2 - read-input"
    (is (= {1 [43 19]
            2 [2 29 14]}
           (#'advent-of-code-2020.day22/read-input example-input-2)))))

(deftest day22-2-example-2-test
  (testing "Day 22 - Part 2 - example 2"
    (is (= 105
           (day22-2
            (#'advent-of-code-2020.day22/read-input example-input-2))))))

(deftest day22-2-example-1-test
  (testing "Day 22 - Part 2 - example"
    (is (= 291
           (day22-2
            (#'advent-of-code-2020.day22/read-input example-input))))))

(deftest day22-2-test
  (testing "Day 22 - Part 2"
    (is (= 36246 (day22-2)))))