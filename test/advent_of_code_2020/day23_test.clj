(ns advent-of-code-2020.day23-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day23 :refer :all]))

(defonce ^:private example-input "389125467")

(deftest day23-1-example-read-input-test
  (testing "Day 23 - Part 1 - example - read-input"
    (let [state (#'advent-of-code-2020.day23/read-input example-input)]
      (are [x y] (= x y)
        [0 2 5 8 6 4 7 3 9 1] (vec (:cups state))
        3 (:cup state)
        1 (:min state)
        9 (:max state)))))

(defn- move-steps [input]
  (iterate #'advent-of-code-2020.day23/move
           (#'advent-of-code-2020.day23/read-input input)))

(deftest day23-1-example-move-1-test
  (testing "Day 23 - Part 1 - example - move 1"
    (let [state (nth (move-steps example-input) 1)]
      (are [x y] (= x y)
        [0 5 8 2 6 4 7 3 9 1] (vec (:cups state))
        2 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-move-2-test
  (testing "Day 23 - Part 1 - example - move 2"
    (let [state (nth (move-steps example-input) 2)]
      (are [x y] (= x y)
        [0 3 5 2 6 4 7 8 9 1] (vec (:cups state))
        5 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-move-3-test
  (testing "Day 23 - Part 1 - example - move 3"
    (let [state (nth (move-steps example-input) 3)]
      (are [x y] (= x y)
        [0 3 5 4 6 8 7 2 9 1] (vec (:cups state))
        8 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-move-4-test
  (testing "Day 23 - Part 1 - example - move 4"
    (let [state (nth (move-steps example-input) 4)]
      (are [x y] (= x y)
        [0 3 5 2 6 8 7 9 4 1] (vec (:cups state))
        4 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-move-5-test
  (testing "Day 23 - Part 1 - example - move 5"
    (let [state (nth (move-steps example-input) 5)]
      (are [x y] (= x y)
        [0 3 5 6 1 8 7 9 4 2] (vec (:cups state))
        1 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-move-6-test
  (testing "Day 23 - Part 1 - example - move 6"
    (let [state (nth (move-steps example-input) 6)]
      (are [x y] (= x y)
        [0 9 5 6 1 8 7 2 4 3] (vec (:cups state))
        9 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-move-10-test
  (testing "Day 23 - Part 1 - example - move 10"
    (let [state (nth (move-steps example-input) 10)]
      (are [x y] (= x y)
        [0 9 6 7 1 8 5 4 3 2] (vec (:cups state))
        8 (:cup state)
        1 (:min state)
        9 (:max state)))))

(deftest day23-1-example-10-test
  (testing "Day 23 - Part 1 - example - 10"
    (is (= 92658374
           (day23-1
            (#'advent-of-code-2020.day23/read-input example-input) 10)))))

(deftest day23-1-example-100-test
  (testing "Day 23 - Part 1 - example - 100"
    (is (= 67384529
           (day23-1
            (#'advent-of-code-2020.day23/read-input example-input))))))

(deftest day23-1-test
  (testing "Day 23 - Part 1"
    (is (= 43769582 (day23-1)))))

(deftest day23-2-extend-up-to-test
  (testing "Day 23 - Part 2 - extend-up-to"
    (let [state (#'advent-of-code-2020.day23/extend-up-to
                 (#'advent-of-code-2020.day23/read-input example-input) 20)]
      (are [x y] (= x y)
        [0 2 5 8 6 4 7 10 9 1 11 12 13 14 15 16 17 18 19 20 3] (vec (:cups state))
        3 (:cup state)
        1 (:min state)
        20 (:max state)))))

(deftest day23-2-example-test
  (testing "Day 23 - Part 2 - example"
    (is (= 149245887792
           (day23-2
            (#'advent-of-code-2020.day23/read-input example-input))))))

(deftest day23-2-test
  (testing "Day 23 - Part 2"
    (is (= 264692662390 (day23-2)))))