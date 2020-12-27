(ns advent-of-code-2020.day17-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day17 :refer :all]))

(defonce ^:private example-input
  ".#.
..#
###")

(defn- sort-pos [pos]
  (sort-by (fn [p] (reduce (fn [^long r ^long d] (+ (* 10 r) d))
                           (reverse p)))
           pos))

(deftest day17-1-example-1-read-input-test
  (testing "Day 17 - Part 1 - Example 1 - read-input"
    (is (= #{[1 0 0]
             [2 1 0]
             [0 2 0] [1 2 0] [2 2 0]}
           (#'advent-of-code-2020.day17/read-input 3 example-input)))))

(deftest day17-1-example-1-active-neighbours-test
  (testing "Day 17 - Part 1 - Example 1 - active-neighbours"
    (is (= (sort-pos [[2 1 0] [0 2 0] ,,, [2 2 0]])
           (sort-pos
            (#'advent-of-code-2020.day17/active-neighbors
             (#'advent-of-code-2020.day17/read-input 3 example-input)
             [1 2 0]))))))

(deftest day17-1-example-1-change-state-active-test
  (testing "Day 17 - Part 1 - Example 1 - change-state - active"
    (let [c (#'advent-of-code-2020.day17/read-input 3 example-input)]
      (is (= #{[1 0 0]
               [2 1 0]
               [0 2 0] [1 2 0] [2 2 0]}
             (#'advent-of-code-2020.day17/change-state c c [1 2 0]))))))

(deftest day17-1-example-1-change-state-inactive-test
  (testing "Day 17 - Part 1 - Example 1 - change-state - inactive"
    (let [c (#'advent-of-code-2020.day17/read-input 3 example-input)]
      (is (= #{[2 1 0] [0 2 0] [1 2 0] [2 2 0]}
             (#'advent-of-code-2020.day17/change-state c c [1 0 0]))))))

(deftest day17-1-example-1-test
  (testing "Day 17 - Part 1 - Example 1"
    (let [c (#'advent-of-code-2020.day17/read-input 3 example-input)]
      (is (= 11 (day17-1 c 1))))))

(deftest day17-1-example-6-test
  (testing "Day 17 - Part 1 - Example 1"
    (let [c (#'advent-of-code-2020.day17/read-input 3 example-input)]
      (is (= 112 (day17-1 c 6))))))

(deftest day17-1-test
  (testing "Day 17 - Part 1"
    (is (= 304 (day17-1)))))

(deftest day17-2-test
  (testing "Day 17 - Part 2"
    (is (= 1868 (day17-2)))))