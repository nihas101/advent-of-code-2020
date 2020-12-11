(ns advent-of-code-2020.day11-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day11 :refer :all]))

(defonce ^:private example-layout
  "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL")

(deftest day11-1-example-read-layout-test
  (testing "Day 11 - Part 1 - Example 1 - read-layout"
    (is (= example-layout
           (chair-layout->string
            (#'advent-of-code-2020.day11/read-layout example-layout))))))

(def game-of-chairs-1
  (let [lo (#'advent-of-code-2020.day11/read-layout example-layout)
        pos (transduce (comp (filter (fn [[_ c]] (not= c \.)))
                             (map first))
                       conj
                       lo)]
    (iterate (fn [[layout chair-pos]]
               [(#'advent-of-code-2020.day11/game-of-chairs-step
                 #'advent-of-code-2020.day11/neighbouring-freqs
                 layout chair-pos 3)
                chair-pos])
             [lo pos])))

(deftest day11-1-example-game-of-chairs-step-1-test
  (testing "Day 11 - Part 1 - Example 1 - game-of-chairs-step 1"
    (is (= "#.##.##.##
#######.##
#.#.#..#..
####.##.##
#.##.##.##
#.#####.##
..#.#.....
##########
#.######.#
#.#####.##"
           (chair-layout->string
            (first (nth game-of-chairs-1 1)))))))

(deftest day11-1-example-game-of-chairs-step-2-test
  (testing "Day 11 - Part 1 - Example 1 - game-of-chairs-step 2"
    (is (= "#.LL.L#.##
#LLLLLL.L#
L.L.L..L..
#LLL.LL.L#
#.LL.LL.LL
#.LLLL#.##
..L.L.....
#LLLLLLLL#
#.LLLLLL.L
#.#LLLL.##"
           (chair-layout->string
            (first (nth game-of-chairs-1 2)))))))

(deftest day11-1-example-game-of-chairs-step-3-test
  (testing "Day 11 - Part 1 - Example 1 - game-of-chairs-step 3"
    (is (= "#.##.L#.##
#L###LL.L#
L.#.#..#..
#L##.##.L#
#.##.LL.LL
#.###L#.##
..#.#.....
#L######L#
#.LL###L.L
#.#L###.##"
           (chair-layout->string
            (first (nth game-of-chairs-1 3)))))))

(deftest day11-1-example-game-of-chairs-step-4-test
  (testing "Day 11 - Part 1 - Example 1 - game-of-chairs-step 4"
    (is (= "#.#L.L#.##
#LLL#LL.L#
L.L.L..#..
#LLL.##.L#
#.LL.LL.LL
#.LL#L#.##
..L.L.....
#L#LLLL#L#
#.LLLLLL.L
#.#L#L#.##"
           (chair-layout->string
            (first (nth game-of-chairs-1 4)))))))

(deftest day11-1-example-game-of-chairs-step-5-test
  (testing "Day 11 - Part 1 - Example 1 - game-of-chairs-step 5"
    (is (= "#.#L.L#.##
#LLL#LL.L#
L.#.L..#..
#L##.##.L#
#.#L.LL.LL
#.#L#L#.##
..L.L.....
#L#L##L#L#
#.LLLLLL.L
#.#L#L#.##"
           (chair-layout->string
            (first (nth game-of-chairs-1 5)))))))

(deftest day11-1-example-1-test
  (testing "Day 11 - Part 1 - Example 1"
    (is (= 37
           (day11-1 (#'advent-of-code-2020.day11/read-layout example-layout))))))

(deftest day11-1-test
  (testing "Day 11 - Part 1"
    (is (= 2412 (day11-1)))))

(deftest day11-2-in-view-freqs-example-1-test
  (testing "Day 11 - Part 2 - in-view-freqs - Example"
    (is (= {\# 8}
           (#'advent-of-code-2020.day11/in-view-freqs
            (#'advent-of-code-2020.day11/read-layout ".......#.
...#.....
.#.......
.........
..#L....#
....#....
.........
#........
...#.....") 3 4)))))

(deftest day11-2-in-view-freqs-example-2-test
  (testing "Day 11 - Part 2 - in-view-freqs - Example 2"
    (is (= {\L 1 nil 7}
           (#'advent-of-code-2020.day11/in-view-freqs
            (#'advent-of-code-2020.day11/read-layout ".............
.L.L.#.#.#.#.
.............") 1 1)))))

(deftest day11-2-in-view-freqs-example-3-test
  (testing "Day 11 - Part 2 - in-view-freqs - Example 3"
    (is (= {nil 8}
           (#'advent-of-code-2020.day11/in-view-freqs
            (#'advent-of-code-2020.day11/read-layout ".##.##.
#.#.#.#
##...##
...L...
##...##
#.#.#.#
.##.##.") 3 3)))))

(def game-of-chairs-2
  (let [lo (#'advent-of-code-2020.day11/read-layout example-layout)
        pos (transduce (comp (filter (fn [[_ c]] (not= c \.)))
                             (map first))
                       conj
                       lo)]
    (iterate (fn [[layout chair-pos]]
               [(#'advent-of-code-2020.day11/game-of-chairs-step
                 #'advent-of-code-2020.day11/in-view-freqs
                 layout chair-pos 4)
                chair-pos])
             [lo pos])))

(deftest day11-2-example-game-of-chairs-step-1-test
  (testing "Day 11 - Part 2 - Example 1 - game-of-chairs-step 1"
    (is (= "#.##.##.##
#######.##
#.#.#..#..
####.##.##
#.##.##.##
#.#####.##
..#.#.....
##########
#.######.#
#.#####.##"
           (chair-layout->string
            (first (nth game-of-chairs-2 1)))))))

(deftest day11-2-example-game-of-chairs-step-2-test
  (testing "Day 11 - Part 2 - Example 1 - game-of-chairs-step 2"
    (is (= "#.LL.LL.L#
#LLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLL#
#.LLLLLL.L
#.LLLLL.L#"
           (chair-layout->string
            (first (nth game-of-chairs-2 2)))))))

(deftest day11-2-example-game-of-chairs-step-3-test
  (testing "Day 11 - Part 2 - Example 1 - game-of-chairs-step 3"
    (is (= "#.L#.##.L#
#L#####.LL
L.#.#..#..
##L#.##.##
#.##.#L.##
#.#####.#L
..#.#.....
LLL####LL#
#.L#####.L
#.L####.L#"
           (chair-layout->string
            (first (nth game-of-chairs-2 3)))))))

(deftest day11-2-example-game-of-chairs-step-4-test
  (testing "Day 11 - Part 2 - Example 1 - game-of-chairs-step 4"
    (is (= "#.L#.L#.L#
#LLLLLL.LL
L.L.L..#..
##LL.LL.L#
L.LL.LL.L#
#.LLLLL.LL
..L.L.....
LLLLLLLLL#
#.LLLLL#.L
#.L#LL#.L#"
           (chair-layout->string
            (first (nth game-of-chairs-2 4)))))))

(deftest day11-2-example-game-of-chairs-step-5-test
  (testing "Day 11 - Part 2 - Example 1 - game-of-chairs-step 5"
    (is (= "#.L#.L#.L#
#LLLLLL.LL
L.L.L..#..
##L#.#L.L#
L.L#.#L.L#
#.L####.LL
..#.#.....
LLL###LLL#
#.LLLLL#.L
#.L#LL#.L#"
           (chair-layout->string
            (first (nth game-of-chairs-2 5)))))))

(deftest day11-2-example-game-of-chairs-step-6-test
  (testing "Day 11 - Part 2 - Example 1 - game-of-chairs-step 6"
    (is (= "#.L#.L#.L#
#LLLLLL.LL
L.L.L..#..
##L#.#L.L#
L.L#.LL.L#
#.LLLL#.LL
..#.L.....
LLL###LLL#
#.LLLLL#.L
#.L#LL#.L#"
           (chair-layout->string
            (first (nth game-of-chairs-2 6)))))))

(deftest day11-2-example-1-test
  (testing "Day 11 - Part 2 - Example 1"
    (is (= 26
           (day11-2 (#'advent-of-code-2020.day11/read-layout example-layout))))))

(deftest day11-2-test
  (testing "Day 11 - Part 2"
    (is (= 2176 (day11-2)))))