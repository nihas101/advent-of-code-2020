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

(def game-of-chairs-1
  (let [lo (#'advent-of-code-2020.day11/read-layout example-layout)
        lo (reduce #'advent-of-code-2020.day11/neighbors lo (keys lo))
        pos (transduce (comp (filter (fn [[_ [c]]] (not= c \.)))
                             (map first))
                       conj
                       lo)]
    (iterate (fn [[layout chair-pos]]
               [(#'advent-of-code-2020.day11/game-of-chairs-step
                 layout
                 chair-pos 3)
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
    (let [lo (#'advent-of-code-2020.day11/read-layout ".......#.
...#.....
.#.......
.........
..#L....#
....#....
.........
#........
...#.....")]
      (is (= {\# 8}
             (#'advent-of-code-2020.day11/chair-freqs
              (reduce (partial #'advent-of-code-2020.day11/in-view lo)
                      lo (keys lo)) 3 4))))))

(deftest day11-2-in-view-freqs-example-2-test
  (testing "Day 11 - Part 2 - in-view-freqs - Example 2"
    (let [lo (#'advent-of-code-2020.day11/read-layout ".............
.L.L.#.#.#.#.
.............")]
      (is (= {\L 1 nil 7}
             (#'advent-of-code-2020.day11/chair-freqs
              (reduce (partial #'advent-of-code-2020.day11/in-view lo) lo (keys lo))
              1 1))))))

(deftest day11-2-in-view-freqs-example-3-test
  (testing "Day 11 - Part 2 - in-view-freqs - Example 3"
    (let [lo (#'advent-of-code-2020.day11/read-layout ".##.##.
#.#.#.#
##...##
...L...
##...##
#.#.#.#
.##.##.")]
      (is (= {nil 8}
             (#'advent-of-code-2020.day11/chair-freqs
              (reduce (partial #'advent-of-code-2020.day11/in-view lo) lo (keys lo))
              3 3))))))

(def game-of-chairs-2
  (let [lo (#'advent-of-code-2020.day11/read-layout example-layout)
        lo (reduce (partial #'advent-of-code-2020.day11/in-view lo) lo (keys lo))
        pos (transduce (comp (filter (fn [[_ [c]]] (not= c \.)))
                             (map first))
                       conj
                       lo)]
    (iterate (fn [[layout chair-pos]]
               [(#'advent-of-code-2020.day11/game-of-chairs-step
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