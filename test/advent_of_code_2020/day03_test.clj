(ns advent-of-code-2020.day03-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day03 :refer :all]))

(defonce test-map
  ["..##......."
   "#...#...#.."
   ".#....#..#."
   "..#.#...#.#"
   ".#...##..#."
   "..#.##....."
   ".#.#.#....#"
   ".#........#"
   "#.##...#..."
   "#...##....#"
   ".#..#...#.#"])

(deftest day3-1-example-tree-map-test
  (testing "Tree map"
    (is (= {0 #{2 3}
            1 #{0 4 8}
            2 #{1 6 9}
            3 #{2 4 8 10}
            4 #{1 5 6 9}
            5 #{2 4 5}
            6 #{1 3 5 10}
            7 #{1 10}
            8 #{0 2 3 7}
            9 #{0 4 5 10}
            10 #{1 4 8 10}
            :height 11
            :width 11}
           (#'advent-of-code-2020.day03/tree-map test-map)))))

(deftest day3-1-example-follow-slope-test
  (testing "Follow slope"
    (is (= [[3 1] [6 2] [9 3] [1 4] [4 5] [7 6] [10 7] [2 8] [5 9] [8 10]]
           (#'advent-of-code-2020.day03/follow-slope
            {0 #{2 3}
             1 #{0 4 8}
             2 #{1 6 9}
             3 #{2 4 8 10}
             4 #{1 5 6 9}
             5 #{2 4 5}
             6 #{1 3 5 10}
             7 #{1 10}
             8 #{0 2 3 7}
             9 #{0 4 5 10}
             10 #{1 4 8 10}
             :height 11
             :width 11}
            3 1)))))

(deftest day3-1-example-test
  (testing "Day 3 - Part 1 - Example"
    (is (= 7
           (day3-1
            {0 #{2 3}
             1 #{0 4 8}
             2 #{1 6 9}
             3 #{2 4 8 10}
             4 #{1 5 6 9}
             5 #{2 4 5}
             6 #{1 3 5 10}
             7 #{1 10}
             8 #{0 2 3 7}
             9 #{0 4 5 10}
             10 #{1 4 8 10}
             :height 11
             :width 11}
            3 1)))))

(deftest day3-1-test
  (testing "Day 3 - Part 1 - Example"
    (is (= 178 (day3-1 3 1)))))

(defonce parsed-tree-map
  {0 #{2 3}
   1 #{0 4 8}
   2 #{1 6 9}
   3 #{2 4 8 10}
   4 #{1 5 6 9}
   5 #{2 4 5}
   6 #{1 3 5 10}
   7 #{1 10}
   8 #{0 2 3 7}
   9 #{0 4 5 10}
   10 #{1 4 8 10}
   :height 11
   :width 11})

(deftest day3-2-example-additional-test
  (testing "Day 3 - Part 2 - Example - Additional tests"
    (are [x y] (= x y)
      2 (day3-1 parsed-tree-map 1 1)
      3 (day3-1 parsed-tree-map 5 1)
      4 (day3-1 parsed-tree-map 7 1)
      2 (day3-1 parsed-tree-map 1 2))))

(deftest day3-2-example-test
  (testing "Day 3 - Part 2 - Example"
    (is (= 336 (day3-2 parsed-tree-map)))))

(deftest day3-2-test
  (testing "Day 3 - Part 2 - Example"
    (is (= 3492520200 (day3-2)))))