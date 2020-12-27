(ns advent-of-code-2020.day20-test
  (:require
   [clojure.string :as string]
   [clojure.test :refer :all]
   [advent-of-code-2020.day20 :refer :all]))

(defonce ^:private example-input
  "Tile 2311:
..##.#..#.
##..#.....
#...##..#.
####.#...#
##.##.###.
##...#.###
.#.#.#..##
..#....#..
###...#.#.
..###..###

Tile 1951:
#.##...##.
#.####...#
.....#..##
#...######
.##.#....#
.###.#####
###.##.##.
.###....#.
..#.#..#.#
#...##.#..

Tile 1171:
####...##.
#..##.#..#
##.#..#.#.
.###.####.
..###.####
.##....##.
.#...####.
#.##.####.
####..#...
.....##...

Tile 1427:
###.##.#..
.#..#.##..
.#.##.#..#
#.#.#.##.#
....#...##
...##..##.
...#.#####
.#.####.#.
..#..###.#
..##.#..#.

Tile 1489:
##.#.#....
..##...#..
.##..##...
..#...#...
#####...#.
#..#.#.#.#
...#.#.#..
##.#...##.
..##.##.##
###.##.#..

Tile 2473:
#....####.
#..#.##...
#.##..#...
######.#.#
.#...#.#.#
.#########
.###.#..#.
########.#
##...##.#.
..###.#.#.

Tile 2971:
..#.#....#
#...###...
#.#.###...
##.##..#..
.#####..##
.#..####.#
#..#.#..#.
..####.###
..#.#.###.
...#.#.#.#

Tile 2729:
...#.#.#.#
####.#....
..#.#.....
....#..#.#
.##..##.#.
.#.####...
####.#.#..
##.####...
##..#.##..
#.##...##.

Tile 3079:
#.#.#####.
.#..######
..#.......
######....
####.#..#.
.#...#.##.
#.#####.##
..#.###...
..#.......
..#.###...")

(deftest day20-read-example-input-test
  (testing "Day 20 - Part 1 - read example-input"
    (is (= [{:image {:height 10
                     :width 10
                     [0 1] 1 [0 2] 1 [0 3] 1 [0 4] 1 [0 5] 1 [0 8] 1
                     [1 1] 1 [1 3] 1 [1 4] 1 [1 5] 1 [1 6] 1 [1 8] 1
                     [2 0] 1 [2 3] 1 [2 7] 1 [2 8] 1 [2 9] 1
                     [3 0] 1 [3 3] 1 [3 4] 1 [3 6] 1 [3 9] 1
                     [4 1] 1 [4 2] 1 [4 4] 1 [4 9] 1
                     [5 0] 1 [5 2] 1 [5 3] 1 [5 5] 1 [5 6] 1
                     [6 4] 1 [6 8] 1
                     [7 4] 1 [7 5] 1 [7 7] 1 [7 9] 1
                     [8 0] 1 [8 2] 1 [8 4] 1 [8 5] 1 [8 6] 1 [8 8] 1 [8 9] 1
                     [9 3] 1 [9 5] 1 [9 6] 1 [9 9] 1}
             :tile-id 2311}
            {:image {:height 10
                     :width 10
                     [0 0] 1 [0 1] 1 [0 3] 1 [0 6] 1 [0 9] 1
                     [1 4] 1 [1 5] 1 [1 6] 1 [1 7] 1
                     [2 0] 1 [2 1] 1 [2 4] 1 [2 5] 1 [2 6] 1 [2 7] 1 [2 8] 1
                     [3 0] 1 [3 1] 1 [3 5] 1 [3 7] 1
                     [4 1] 1 [4 3] 1 [4 4] 1 [4 6] 1 [4 8] 1 [4 9] 1
                     [5 1] 1 [5 2] 1 [5 3] 1 [5 5] 1 [5 6] 1 [5 9] 1
                     [6 3] 1 [6 5] 1
                     [7 0] 1 [7 3] 1 [7 5] 1 [7 6] 1 [7 8] 1 [7 9] 1
                     [8 0] 1 [8 2] 1 [8 3] 1 [8 5] 1 [8 6] 1 [8 7] 1
                     [9 1] 1 [9 2] 1 [9 3] 1 [9 4] 1 [9 5] 1 [9 8] 1}
             :tile-id 1951}
            {:image {:height 10
                     :width 10
                     [0 0] 1 [0 1] 1 [0 2] 1 [0 7] 1 [0 8] 1
                     [1 0] 1 [1 2] 1 [1 3] 1 [1 5] 1 [1 6] 1 [1 8] 1
                     [2 0] 1 [2 3] 1 [2 4] 1 [2 5] 1 [2 7] 1 [2 8] 1
                     [3 0] 1 [3 1] 1 [3 2] 1 [3 3] 1 [3 4] 1 [3 7] 1 [3 8] 1
                     [4 1] 1 [4 4] 1
                     [5 3] 1 [5 6] 1 [5 7] 1 [5 9] 1
                     [6 1] 1 [6 2] 1 [6 3] 1 [6 4] 1 [6 6] 1 [6 7] 1 [6 8] 1 [6 9] 1
                     [7 0] 1 [7 3] 1 [7 4] 1 [7 5] 1 [7 6] 1 [7 7] 1
                     [8 0] 1 [8 2] 1 [8 3] 1 [8 4] 1 [8 5] 1 [8 6] 1 [8 7] 1
                     [9 1] 1 [9 4] 1}
             :tile-id 1171}
            {:image {:height 10
                     :width 10
                     [0 0] 1 [0 3] 1
                     [1 0] 1 [1 1] 1 [1 2] 1 [1 7] 1
                     [2 0] 1 [2 3] 1 [2 8] 1 [2 9] 1
                     [3 2] 1 [3 5] 1 [3 6] 1 [3 7] 1 [3 9] 1
                     [4 0] 1 [4 1] 1 [4 2] 1 [4 3] 1 [4 4] 1 [4 5] 1 [4 7] 1
                     [5 0] 1 [5 6] 1 [5 7] 1 [5 8] 1 [5 9] 1
                     [6 1] 1 [6 2] 1 [6 3] 1 [6 6] 1 [6 7] 1 [6 8] 1
                     [7 0] 1 [7 1] 1 [7 3] 1 [7 5] 1 [7 6] 1 [7 8] 1
                     [8 4] 1 [8 5] 1 [8 6] 1 [8 7] 1 [8 9] 1
                     [9 2] 1 [9 3] 1 [9 4] 1 [9 6] 1 [9 8] 1}
             :tile-id 1427}
            {:image {:height 10
                     :width 10
                     [0 0] 1 [0 4] 1 [0 5] 1 [0 7] 1 [0 9] 1
                     [1 0] 1 [1 2] 1 [1 4] 1 [1 7] 1 [1 9] 1
                     [2 1] 1 [2 2] 1 [2 3] 1 [2 4] 1 [2 8] 1 [2 9] 1
                     [3 0] 1 [3 1] 1 [3 4] 1 [3 5] 1 [3 6] 1 [3 7] 1 [3 8] 1
                     [4 4] 1 [4 9] 1
                     [5 0] 1 [5 2] 1 [5 5] 1 [5 6] 1 [5 8] 1 [5 9] 1
                     [6 2] 1 [6 3] 1 [6 8] 1
                     [7 1] 1 [7 5] 1 [7 6] 1 [7 7] 1 [7 9] 1
                     [8 4] 1 [8 7] 1 [8 8] 1
                     [9 5] 1 [9 8] 1}
             :tile-id 1489}
            {:image {:height 10
                     :width 10
                     [0 0] 1 [0 1] 1 [0 2] 1 [0 3] 1 [0 7] 1 [0 8] 1
                     [1 3] 1 [1 4] 1 [1 5] 1 [1 6] 1 [1 7] 1 [1 8] 1
                     [2 2] 1 [2 3] 1 [2 5] 1 [2 6] 1 [2 7] 1 [2 9] 1
                     [3 1] 1 [3 2] 1 [3 3] 1 [3 5] 1 [3 6] 1 [3 7] 1 [3 9] 1
                     [4 3] 1 [4 5] 1 [4 7] 1 [4 9] 1
                     [5 0] 1 [5 1] 1 [5 3] 1 [5 4] 1 [5 5] 1 [5 6] 1 [5 7] 1 [5 8] 1
                     [6 0] 1 [6 1] 1 [6 2] 1 [6 5] 1 [6 7] 1 [6 8] 1 [6 9] 1
                     [7 0] 1 [7 3] 1 [7 4] 1 [7 5] 1 [7 7] 1
                     [8 0] 1 [8 5] 1 [8 6] 1 [8 8] 1 [8 9] 1
                     [9 3] 1 [9 4] 1 [9 5] 1 [9 7] 1}
             :tile-id 2473}
            {:image {:height 10
                     :width 10
                     [0 1] 1 [0 2] 1 [0 3] 1 [0 6] 1
                     [1 3] 1 [1 4] 1 [1 5] 1
                     [2 0] 1 [2 2] 1 [2 4] 1 [2 7] 1 [2 8] 1
                     [3 3] 1 [3 4] 1 [3 6] 1 [3 7] 1 [3 9] 1
                     [4 0] 1 [4 1] 1 [4 2] 1 [4 3] 1 [4 4] 1 [4 5] 1 [4 7] 1 [4 8] 1
                     [5 1] 1 [5 2] 1 [5 4] 1 [5 5] 1 [5 6] 1 [5 7] 1 [5 9] 1
                     [6 1] 1 [6 2] 1 [6 5] 1 [6 8] 1
                     [7 3] 1 [7 5] 1 [7 7] 1 [7 8] 1 [7 9] 1
                     [8 4] 1 [8 6] 1 [8 7] 1 [8 8] 1 [9 0] 1 [9 4] 1 [9 5] 1 [9 7] 1 [9 9] 1}
             :tile-id 2971}
            {:image {:height 10
                     :width 10
                     [0 1] 1 [0 6] 1 [0 7] 1 [0 8] 1 [0 9] 1
                     [1 1] 1 [1 4] 1 [1 5] 1 [1 6] 1 [1 7] 1 [1 8] 1
                     [2 1] 1 [2 2] 1 [2 4] 1 [2 6] 1 [2 9] 1
                     [3 0] 1 [3 1] 1 [3 5] 1 [3 6] 1 [3 7] 1 [3 9] 1
                     [4 2] 1 [4 3] 1 [4 5] 1 [4 7] 1 [4 8] 1
                     [5 0] 1 [5 1] 1 [5 4] 1 [5 5] 1 [5 6] 1 [5 7] 1
                     [6 4] 1 [6 5] 1 [6 7] 1 [6 8] 1
                     [7 0] 1 [7 3] 1 [7 6] 1 [7 8] 1 [7 9] 1
                     [8 4] 1 [8 9] 1 [9 0] 1 [9 3] 1}
             :tile-id 2729}
            {:image {:height 10
                     :width 10
                     [0 0] 1 [0 3] 1 [0 4] 1 [0 6] 1
                     [1 1] 1 [1 3] 1 [1 4] 1 [1 5] 1
                     [2 0] 1 [2 2] 1 [2 3] 1 [2 4] 1 [2 6] 1 [2 7] 1 [2 8] 1 [2 9] 1
                     [3 3] 1 [3 4] 1 [3 6] 1 [4 0] 1 [4 1] 1 [4 3] 1 [4 6] 1 [4 7] 1 [4 9] 1
                     [5 0] 1 [5 1] 1 [5 3] 1 [5 4] 1 [5 5] 1 [5 6] 1 [5 7] 1 [5 9] 1
                     [6 0] 1 [6 1] 1 [6 6] 1 [6 7] 1 [6 9] 1
                     [7 0] 1 [7 1] 1 [7 5] 1
                     [8 0] 1 [8 1] 1 [8 4] 1 [8 5] 1 [8 6] 1
                     [9 1] 1 [9 6] 1}
             :tile-id 3079}]
           (#'advent-of-code-2020.day20/read-input example-input)))))

(deftest day20-example-serialize-image-only-border-test
  (testing "Day 20 - Part 1 - serialize-image only border"
    (let [im "Tile 1:\n#####\n#...#\n#...#\n#...#\n#####"]
      (is (= {:north [31 31]
              :east [31 31]
              :south [31 31]
              :west [31 31]
              :content {:height 3 :width 3}
              :flipped false}
             (#'advent-of-code-2020.day20/serialize-image
              (-> (#'advent-of-code-2020.day20/read-tile im) :image)))))))

(deftest day20-example-serialize-image-no-border-test
  (testing "Day 20 - Part 1 - serialize-image no border"
    (let [im "Tile 1:\n.....\n.###.\n.###.\n.###.\n....."]
      (is (= {:north [0 0]
              :east [0 0]
              :south [0 0]
              :west [0 0]
              :content {:height 3
                        :width 3
                        [0 0] 1 [0 1] 1 [0 2] 1
                        [1 0] 1 [1 1] 1 [1 2] 1
                        [2 0] 1 [2 1] 1 [2 2] 1}
              :flipped false}
             (#'advent-of-code-2020.day20/serialize-image
              (-> (#'advent-of-code-2020.day20/read-tile im) :image)))))))

(deftest day20-example-serialize-image-test
  (testing "Day 20 - Part 1 - serialize-image"
    (let [im {:height 10
              :width 10
              [0 1] 1 [0 2] 1 [0 3] 1 [0 4] 1 [0 5] 1 [0 8] 1
              [1 1] 1 [1 3] 1 [1 4] 1 [1 5] 1 [1 6] 1 [1 8] 1
              [2 0] 1 [2 3] 1 [2 7] 1 [2 8] 1 [2 9] 1
              [3 0] 1 [3 3] 1 [3 4] 1 [3 6] 1 [3 9] 1
              [4 1] 1 [4 2] 1 [4 4] 1 [4 9] 1
              [5 0] 1 [5 2] 1 [5 3] 1 [5 5] 1 [5 6] 1
              [6 4] 1 [6 8] 1 [7 4] 1 [7 5] 1 [7 7] 1 [7 9] 1
              [8 0] 1 [8 2] 1 [8 4] 1 [8 5] 1 [8 6] 1 [8 8] 1 [8 9] 1
              [9 3] 1 [9 5] 1 [9 6] 1 [9 9] 1}]
      (is (= {:north [210 300]
              :east [89 616]
              :south [231 924]
              :west [498 318]
              :content {:height 8
                        :width 8
                        [0 0] 1 [0 2] 1 [0 3] 1 [0 4] 1 [0 5] 1 [0 7] 1
                        [1 2] 1 [1 6] 1 [1 7] 1
                        [2 2] 1 [2 3] 1 [2 5] 1
                        [3 0] 1 [3 1] 1 [3 3] 1
                        [4 1] 1 [4 2] 1 [4 4] 1 [4 5] 1
                        [5 3] 1 [5 7] 1
                        [6 3] 1 [6 4] 1 [6 6] 1
                        [7 1] 1 [7 3] 1 [7 4] 1 [7 5] 1 [7 7] 1}
              :flipped false}
             (#'advent-of-code-2020.day20/serialize-image im))))))

(deftest day20-example-rotate-border-test
  (testing "Day 20 - Part 1 - rotate-border"
    (let [edges {:north [210 300]
                 :east [89 616]
                 :south [231 924]
                 :west [498 318]
                 :flipped false}]
      (is (= {:east [924 231]
              :flipped false
              :north [89 616]
              :rotation 90
              :south [498 318]
              :west [300 210]}
             (#'advent-of-code-2020.day20/rotate-border edges))))))

(deftest day20-example-flip-edges-test
  (testing "Day 20 - Part 1 - flip-edges"
    (let [edges {:north [210 300]
                 :east [89 616]
                 :south [231 924]
                 :west [498 318]
                 :flipped false}]
      (is (= {:east [616 89]
              :flipped true
              :north [231 924]
              :south [210 300]
              :west [318 498]}
             (#'advent-of-code-2020.day20/flip-border edges))))))

(deftest day20-example-get-borders-test
  (testing "Day 20 - Part 1 - get-borders"
    (let [borders {:north [210 300]
                   :east [89 616]
                   :south [231 924]
                   :west [498 318]
                   :flipped false}]
      (are [x y] (= x y)
        [210 89 231 498] (#'advent-of-code-2020.day20/get-border-values borders)
        [231 616 210 318] (#'advent-of-code-2020.day20/get-border-values
                           (#'advent-of-code-2020.day20/flip-border borders))))))

(deftest day20-1-example-test
  (testing "Day 20 - Part 1 - example"
    (is (= 20899048083289
           (day20-1
            (#'advent-of-code-2020.day20/read-input example-input))))))

(deftest day20-1-test
  (testing "Day 20 - Part 1"
    (is (= 79412832860579 (day20-1)))))

(deftest day20-example-transforms-test
  (testing "Day 20 - Part 2 - possible-transforms"
    (let [im "Tile 1:\n.####\n.....\n#...#\n#...#\n#.###"]
      (is (= [{:image {:content {:height 3 :width 3}
                       :east [23 29]
                       :flipped false
                       :north [15 30]
                       :south [23 29]
                       :west [7 28]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [29 23]
                       :flipped false
                       :north [23 29]
                       :rotation 90
                       :south [7 28]
                       :west [30 15]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [28 7]
                       :flipped false
                       :north [29 23]
                       :rotation 180
                       :south [30 15]
                       :west [29 23]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [15 30]
                       :flipped false
                       :north [28 7]
                       :rotation 270
                       :south [29 23]
                       :west [23 29]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [29 23]
                       :flipped true
                       :north [23 29]
                       :south [15 30]
                       :west [28 7]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [23 29]
                       :flipped true
                       :north [7 28]
                       :rotation 90
                       :south [23 29]
                       :west [15 30]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [7 28]
                       :flipped true
                       :north [30 15]
                       :rotation 180
                       :south [29 23]
                       :west [23 29]}
               :tile-id 1}
              {:image {:content {:height 3 :width 3}
                       :east [30 15]
                       :flipped true
                       :north [29 23]
                       :rotation 270
                       :south [28 7]
                       :west [29 23]}
               :tile-id 1}]
             (#'advent-of-code-2020.day20/possible-transforms
              (update (#'advent-of-code-2020.day20/read-tile im)
                      :image #'advent-of-code-2020.day20/serialize-image)))))))

(deftest day20-2-rotate-around-origin-test
  (testing "Day 20 - Part 2 - rotate around origin"
    (are [x y] (= x (mapv long y))
      [0 -1] (#'advent-of-code-2020.day20/rotate [1  0] 90)
      [1  0] (#'advent-of-code-2020.day20/rotate [0  1] 90)
      [0  1] (#'advent-of-code-2020.day20/rotate [-1 0] 90)
      [-1 0] (#'advent-of-code-2020.day20/rotate [0 -1] 90)

      [-1 0] (#'advent-of-code-2020.day20/rotate [1  0] 180)
      [0 -1] (#'advent-of-code-2020.day20/rotate [0  1] 180)
      [1  0] (#'advent-of-code-2020.day20/rotate [-1 0] 180)
      [0  1] (#'advent-of-code-2020.day20/rotate [0 -1] 180)

      [0  1] (#'advent-of-code-2020.day20/rotate [1  0] 270)
      [-1 0] (#'advent-of-code-2020.day20/rotate [0  1] 270)
      [0 -1] (#'advent-of-code-2020.day20/rotate [-1 0] 270)
      [1  0] (#'advent-of-code-2020.day20/rotate [0 -1] 270))))

(deftest day20-2-rotate-test
  (testing "Day 20 - Part 2 - rotate"
    (are [x y] (= x y)
      [1 -1] (#'advent-of-code-2020.day20/rotate [1 0] [2  0] 90)
      [2  0] (#'advent-of-code-2020.day20/rotate [1 0] [1  1] 90)
      [1  1] (#'advent-of-code-2020.day20/rotate [1 0] [0  0] 90)
      [0  0] (#'advent-of-code-2020.day20/rotate [1 0] [1 -1] 90)
      [0  8] (#'advent-of-code-2020.day20/rotate [4 4] [0  0] 90)
      [8  8] (#'advent-of-code-2020.day20/rotate [4 4] [0  0] 180)
      [8  0] (#'advent-of-code-2020.day20/rotate [4 4] [0  0] 270)
      [8  8] (#'advent-of-code-2020.day20/rotate [4 4] [0  0] 180))))

(deftest day20-2-flip-test
  (testing "Day 20 - Part 2 - flip"
    (are [x y] (= x y)
      [1 9] (#'advent-of-code-2020.day20/flip [1 0] 10)
      [0 8] (#'advent-of-code-2020.day20/flip [0 1] 10)
      [9 9] (#'advent-of-code-2020.day20/flip [9 0] 10)
      [0 0] (#'advent-of-code-2020.day20/flip [0 9] 10))))

(defonce ^:private test-image-tiles
  "Tile 1:
#.#.#
.#..#
#.#.#
...##
...##

Tile 2:
....#
#..#.
#.#..
##...
###..

Tile 3:
...##
...##
..#.#
.#..#
#...#

Tile 4:
###..
##...
#.#..
#..#.
#...#")

(deftest day20-2-test-image-tiles-test
  (testing "Day 20 - Part 2 - test-image-tiles"
    (let [tile (update
                (first (#'advent-of-code-2020.day20/read-input test-image-tiles))
                :image #'advent-of-code-2020.day20/serialize-image)]
      (is (= "#..\n.#.\n..#"
             (->> tile
                  (#'advent-of-code-2020.day20/rotate-content)
                  (#'advent-of-code-2020.day20/flip-content)
                  (#'advent-of-code-2020.day20/image-tile->row-strings)
                  (string/join "\n")))))))

(deftest day20-2-test-image-tiles-flipped-test
  (testing "Day 20 - Part 2 - test-image-tiles"
    (let [tile (-> (first (#'advent-of-code-2020.day20/read-input test-image-tiles))
                   (update ,,, :image #'advent-of-code-2020.day20/serialize-image)
                   (update ,,, :image #'advent-of-code-2020.day20/flip-border))]
      (is (= "..#\n.#.\n#.."
             (->> tile
                  (#'advent-of-code-2020.day20/rotate-content)
                  (#'advent-of-code-2020.day20/flip-content)
                  (#'advent-of-code-2020.day20/image-tile->row-strings)
                  (string/join "\n")))))))

(deftest day20-2-test-image-tiles-rotation-test
  (testing "Day 20 - Part 2 - test-image-tiles"
    (let [tile (-> (first (#'advent-of-code-2020.day20/read-input test-image-tiles))
                   (update ,,, :image #'advent-of-code-2020.day20/serialize-image)
                   (update ,,, :image #'advent-of-code-2020.day20/rotate-border))]
      (is (= "..#\n.#.\n#.."
             (->> tile
                  (#'advent-of-code-2020.day20/rotate-content)
                  (#'advent-of-code-2020.day20/flip-content)
                  (#'advent-of-code-2020.day20/image-tile->row-strings)
                  (string/join "\n")))))))

(deftest day20-2-test-image-tiles-flipped-rotation-test
  (testing "Day 20 - Part 2 - test-image-tiles"
    (let [tile (-> (first (#'advent-of-code-2020.day20/read-input test-image-tiles))
                   (update ,,, :image #'advent-of-code-2020.day20/serialize-image)
                   (update ,,, :image #'advent-of-code-2020.day20/rotate-border)
                   (update ,,, :image #'advent-of-code-2020.day20/flip-border))]
      (is (= "#..\n.#.\n..#"
             (->> tile
                  (#'advent-of-code-2020.day20/rotate-content)
                  (#'advent-of-code-2020.day20/flip-content)
                  (#'advent-of-code-2020.day20/image-tile->row-strings)
                  (string/join "\n")))))))

(deftest day20-2-test-image-test
  (testing "Day 20 - Part 2 - test-image-tiles"
    (let [tiles (partition 2 2
                           (mapv
                            #(update % :image #'advent-of-code-2020.day20/serialize-image)
                            (#'advent-of-code-2020.day20/read-input test-image-tiles)))]
      (is (= "#....#\n.#..#.\n..##..\n..##..\n.#..#.\n#....#"
             (#'advent-of-code-2020.day20/stitch-image-together tiles))))))


(deftest day20-2-regex-test
  (testing "Day 20 - Part 2 - regex"
    (is (= (str "..................O.\n"
                "O....OO....OO....OOO\n"
                ".O..O..O..O..O..O...")
           (#'advent-of-code-2020.day20/fix-replace
            (str "..................#.\n"
                 "#....##....##....###\n"
                 ".#..#..#..#..#..#...")
            (#'advent-of-code-2020.day20/sea-monster 1)
            "$1O$2O$3OO$4OO$5OOO$6O$7O$8O$9O$10O$11O")))))

(deftest day20-2-regex-border-test
  (testing "Day 20 - Part 2 - regex"
    (is (= (str "xxxxxxxxxxxxxxxxxxxxxx\n"
                "x..................O.x\n"
                "xO....OO....OO....OOOx\n"
                "x.O..O..O..O..O..O...x\n"
                "xxxxxxxxxxxxxxxxxxxxxx")
           (#'advent-of-code-2020.day20/fix-replace
            (str "xxxxxxxxxxxxxxxxxxxxxx\n"
                 "x..................#.x\n"
                 "x#....##....##....###x\n"
                 "x.#..#..#..#..#..#...x\n"
                 "xxxxxxxxxxxxxxxxxxxxxx")
            (#'advent-of-code-2020.day20/sea-monster 3)
            "$1O$2O$3OO$4OO$5OOO$6O$7O$8O$9O$10O$11O")))))

(deftest day20-2-regex-offset-test
  (testing "Day 20 - Part 2 - regex"
    (is (= (str "xxxxxxxxxxxxxxxxxxxxxx\n"
                "..................O.xx\n"
                "O....OO....OO....OOOxx\n"
                ".O..O..O..O..O..O...xx\n"
                "xxxxxxxxxxxxxxxxxxxxxx")
           (#'advent-of-code-2020.day20/fix-replace
            (str "xxxxxxxxxxxxxxxxxxxxxx\n"
                 "..................#.xx\n"
                 "#....##....##....###xx\n"
                 ".#..#..#..#..#..#...xx\n"
                 "xxxxxxxxxxxxxxxxxxxxxx")
            (#'advent-of-code-2020.day20/sea-monster 3)
            "$1O$2O$3OO$4OO$5OOO$6O$7O$8O$9O$10O$11O")))))

(deftest day20-2-example-test
  (testing "Day 20 - Part 2 - example"
    (is (= 273 (day20-2
                (#'advent-of-code-2020.day20/read-input example-input)
                5)))))

(deftest day20-2-test
  (testing "Day 20 - Part 2 - example"
    (is (= 2155 (day20-2)))))