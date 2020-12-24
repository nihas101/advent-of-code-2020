(ns advent-of-code-2020.day24-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day24 :refer :all]))

(deftest day24-1-read-pos-1-test
  (testing "Day 24 - Part 1 - example - read-pos 1"
    (is (= (#'advent-of-code-2020.day24/read-pos "eee")
           (#'advent-of-code-2020.day24/read-pos "esenee")))))

(deftest day24-1-read-pos-2-test
  (testing "Day 24 - Part 1 - example - read-pos 2"
    (is (= (#'advent-of-code-2020.day24/read-pos "se")
           (#'advent-of-code-2020.day24/read-pos "esew")))))

(deftest day24-1-read-pos-3-test
  (testing "Day 24 - Part 1 - example - read-pos 3"
    (is (= (#'advent-of-code-2020.day24/read-pos "")
           (#'advent-of-code-2020.day24/read-pos "nwwswee")))))

(defonce ^:private example-input
  "sesenwnenenewseeswwswswwnenewsewsw
neeenesenwnwwswnenewnwwsewnenwseswesw
seswneswswsenwwnwse
nwnwneseeswswnenewneswwnewseswneseene
swweswneswnenwsewnwneneseenw
eesenwseswswnenwswnwnwsewwnwsene
sewnenenenesenwsewnenwwwse
wenwwweseeeweswwwnwwe
wsweesenenewnwwnwsenewsenwwsesesenwne
neeswseenwwswnwswswnw
nenwswwsewswnenenewsenwsenwnesesenew
enewnwewneswsewnwswenweswnenwsenwsw
sweneswneswneneenwnewenewwneswswnese
swwesenesewenwneswnwwneseswwne
enesenwswwswneneswsenwnewswseenwsese
wnwnesenesenenwwnenwsewesewsesesew
nenewswnwewswnenesenwnesewesw
eneswnwswnwsenenwnwnwwseeswneewsenese
neswnwewnwnwseenwseesewsenwsweewe
wseweeenwnesenwwwswnew")

(deftest day24-1-read-input-example-test
  (testing "Day 24 - Part 1 - example - read-pos"
    (is (= [[-4 -2] [-1 3] [-3 -3] [2 2] [0 2] [-2 0] [-1 3] [-4 0]
            [-1 1] [-3 -1] [-2 2] [-2 2] [3 3] [-2 0] [2 -2] [0 0]
            [0 2] [2 2] [4 0] [-3 1]]
           (#'advent-of-code-2020.day24/read-input example-input)))))

(deftest day24-1-handle-input-example-test
  (testing "Day 24 - Part 1 - example - handle-input"
    (is (= 10
           (count
            (#'advent-of-code-2020.day24/flip-tiles
             (#'advent-of-code-2020.day24/read-input example-input)))))))

(deftest day24-1-test
  (testing "Day 24 - Part 1"
    (is (= 244 (day24-1)))))

(deftest day24-2-neighbors-test
  (testing "Day 24 - Part 2 - neighbors"
    (is (= [[-1 1] [1 1] [-2 0] [2 0] [-1 -1] [1 -1]]
           (#'advent-of-code-2020.day24/neighbors [0 0])))))

(deftest day24-2-example-test
  (testing "Day 24 - Part 2 - example"
    (let [input (#'advent-of-code-2020.day24/read-input example-input)]
      (are [x y] (= x y)
        10 (day24-2 input 0)
        15 (day24-2 input 1)
        12 (day24-2 input 2)
        25 (day24-2 input 3)
        14 (day24-2 input 4)
        23 (day24-2 input 5)
        28 (day24-2 input 6)
        41 (day24-2 input 7)
        37 (day24-2 input 8)
        49 (day24-2 input 9)
        37 (day24-2 input 10)
        132 (day24-2 input 20)
        259 (day24-2 input 30)
        406 (day24-2 input 40)
        566 (day24-2 input 50)
        788 (day24-2 input 60)
        1106 (day24-2 input 70)
        1373 (day24-2 input 80)
        1844 (day24-2 input 90)
        2208 (day24-2 input 100)))))

(deftest day24-2-test
  (testing "Day 24 - Part 2"
    (is (= 3665 (day24-2)))))