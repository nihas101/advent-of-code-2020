(ns advent-of-code-2020.day23-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day23 :refer :all]))

(defonce ^:private example-input "389125467")

(deftest day23-1-example-read-input-test
  (testing "Day 23 - Part 1 - example - read-input"
    (is (= {:cups {3 8
                   8 9
                   9 1
                   1 2
                   2 5
                   5 4
                   4 6
                   6 7
                   7 3}
            :cup 3
            :min 1
            :max 9}
           (#'advent-of-code-2020.day23/read-input example-input)))))

(def ^:private move-steps
  (iterate #'advent-of-code-2020.day23/move
   (#'advent-of-code-2020.day23/read-input example-input)))

(deftest day23-1-example-move-1-test
  (testing "Day 23 - Part 1 - example - move 1"
    (is (= {:cups {3 2
                   2 8
                   8 9
                   9 1
                   1 5
                   5 4
                   4 6
                   6 7
                   7 3}
            :cup 2
            :min 1
            :max 9}
           (nth move-steps 1)))))

(deftest day23-1-example-move-2-test
  (testing "Day 23 - Part 1 - example - move 2"
    (is (= {:cups {3 2
                   2 5
                   5 4
                   4 6
                   6 7
                   7 8
                   8 9
                   9 1
                   1 3}
            :cup 5
            :min 1
            :max 9}
           (nth move-steps 2)))))

(deftest day23-1-example-move-3-test
  (testing "Day 23 - Part 1 - example - move 3"
    (is (= {:cups {3 4
                   4 6
                   6 7
                   7 2
                   2 5
                   5 8
                   8 9
                   9 1
                   1 3}
            :cup 8
            :min 1
            :max 9}
           (nth move-steps 3)))))

(deftest day23-1-example-move-4-test
  (testing "Day 23 - Part 1 - example - move 4"
    (is (= {:cups {3 2
                   2 5
                   5 8
                   8 4
                   4 6
                   6 7
                   7 9
                   9 1
                   1 3}
            :cup 4
            :min 1
            :max 9}
           (nth move-steps 4)))))

(deftest day23-1-example-move-5-test
  (testing "Day 23 - Part 1 - example - move 5"
    (is (= {:cups {9 2
                   2 5
                   5 8
                   8 4
                   4 1
                   1 3
                   3 6
                   6 7
                   7 9}
            :cup 1
            :min 1
            :max 9}
           (nth move-steps 5)))))

(deftest day23-1-example-move-6-test
  (testing "Day 23 - Part 1 - example - move 6"
    (is (= {:cups {7 2
                   2 5
                   5 8
                   8 4
                   4 1
                   1 9
                   9 3
                   3 6
                   6 7}
            :cup 9
            :min 1
            :max 9}
           (nth move-steps 6)))))

(deftest day23-1-example-move-10-test
  (testing "Day 23 - Part 1 - example - move 10"
    (is (= {:cups {5 8
                   8 3
                   3 7
                   7 4
                   4 1
                   1 9
                   9 2
                   2 6
                   6 5}
            :cup 8
            :min 1
            :max 9}
           (nth move-steps 10)))))

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
    (is (= {:cups {3 8
                   8 9
                   9 1
                   1 2
                   2 5
                   5 4
                   4 6
                   6 7
                   7 10
                   10 11
                   11 12
                   12 13
                   13 14
                   14 15
                   15 16
                   16 17
                   17 18
                   18 19
                   19 20
                   20 3}
            :cup 3
            :min 1
            :max 20}
           (#'advent-of-code-2020.day23/extend-up-to
            (#'advent-of-code-2020.day23/read-input example-input) 20)))))

(deftest day23-2-example-test
  (testing "Day 23 - Part 2 - example - 100"
    (is (= 149245887792
           (day23-2
            (#'advent-of-code-2020.day23/read-input example-input))))))

(deftest day23-2-test
  (testing "Day 23 - Part 2"
    (is (= 264692662390 (time (day23-2))))))