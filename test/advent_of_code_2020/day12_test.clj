(ns advent-of-code-2020.day12-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day12 :refer :all]))

(defonce ^:private example-input
  "F10
N3
F7
R90
F11")

(deftest day12-1-example-1-parse-instructions-test
  (testing "Day 12 - Part 1 - Example 1 - execute-instruction"
    (is (= [[\F 10] [\N 3] [\F 7] [\R 90] [\F 11]]
           (#'advent-of-code-2020.day12/parse-instructions example-input)))))

(deftest day12-1-example-1-execute-instruction-test
  (testing "Day 12 - Part 1 - Example 1 - execute-instruction"
    (is (= {:direction 90 :position [10 0]}
           (execute-instruction ship [\F 10])))))

(deftest day12-1-rotation-test
  (testing "Day 12 - rotation"
    (let [s ship]
      (are [x y] (= x y)
        {:direction 180 :position [0 0]}
        (execute-instruction s [\R 90])

        {:direction 270 :position [0 0]}
        (execute-instruction s [\R 180])

        {:direction 0 :position [0 0]}
        (execute-instruction s [\R 270])

        {:direction 90 :position [0 0]}
        (execute-instruction s [\R 360])

        {:direction 0 :position [0 0]}
        (execute-instruction s [\L 90])

        {:direction 270 :position [0 0]}
        (execute-instruction s [\L 180])

        {:direction 180 :position [0 0]}
        (execute-instruction s [\L 270])

        {:direction 90 :position [0 0]}
        (execute-instruction s [\L 360])))))

(deftest day12-1-example-1-execute-instructions-test
  (testing "Day 12 - Part 1 - Example 1 - execute-instruction"
    (is (= {:direction 180, :position [17 -8]}
           (#'advent-of-code-2020.day12/execute-instructions
            ship
            (#'advent-of-code-2020.day12/parse-instructions example-input))))))

(deftest day12-1-example-1-test
  (testing "Day 12 - Part 1 - Example 1"
    (is (= 25 (day12 ship
                     (#'advent-of-code-2020.day12/parse-instructions example-input))))))

(deftest day12-1-test
  (testing "Day 12 - Part 1"
    (is (= 1956 (day12 ship)))))

(deftest day12-2-example-1-execute-instruction-test
  (testing "Day 12 - Part 2 - Example 1 - execute-instruction"
    (is (= {:position [100 10] :waypoint [10 1]}
           (execute-instruction ship+waypoint [\F 10])))))

(deftest day12-2-rotation-test
  (testing "Day 12 - Part 2 - rotation"
    (let [s ship+waypoint]
      (are [x y] (= x y)
        {:position [0 0] :waypoint [1 -10]}
        (execute-instruction s [\R 90])

        {:position [0 0] :waypoint [-10 -1]}
        (execute-instruction s [\R 180])

        {:position [0 0] :waypoint [-1 10]}
        (execute-instruction s [\R 270])

        {:position [0 0] :waypoint [10 1]}
        (execute-instruction s [\R 360])

        {:position [0 0] :waypoint [-1 10]}
        (execute-instruction s [\L 90])

        {:position [0 0] :waypoint [-10 -1]}
        (execute-instruction s [\L 180])

        {:position [0 0] :waypoint [1 -10]}
        (execute-instruction s [\L 270])

        {:position [0 0] :waypoint [10 1]}
        (execute-instruction s [\L 360])))))

(deftest day12-2-example-1-execute-instructions-test
  (testing "Day 12 - Part 1 - Example 1 - execute-instruction"
    (is (= {:position [214 -72] :waypoint [4 -10]}
           (#'advent-of-code-2020.day12/execute-instructions
            ship+waypoint
            (#'advent-of-code-2020.day12/parse-instructions example-input))))))

(deftest day12-2-example-1-test
  (testing "Day 12 - Part 2 - Example 1"
    (is (= 286 (day12 ship+waypoint
                      (#'advent-of-code-2020.day12/parse-instructions example-input))))))

(deftest day12-2-test
  (testing "Day 12 - Part 2"
    (is (= 126797 (day12 ship+waypoint)))))