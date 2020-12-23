(ns advent-of-code-2020.day13-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day13 :refer :all]))

(defonce ^:private example-input
  "939
7,13,x,x,59,x,31,19")

(deftest day13-1-example-1-read-bus-schedule-test
  (testing "Day 13 - Part 1 - Example 1 - read-bus-schedule"
    (is (= [939 [7 13 nil nil 59 nil 31 19]]
           (#'advent-of-code-2020.day13/read-bus-schedule example-input)))))

(deftest day13-1-example-1-bus-schedule+minutes-to-wait-test
  (testing "Day 13 - Part 1 - Example 1 - bus-schedule+minutes-to-wait"
    (is (= [939 [[nil nil] [nil nil] [nil nil]
                 [59 5] [7 6] [13 10] [19 11] [31 22]]]
           (#'advent-of-code-2020.day13/bus-schedule+minutes-to-wait
            (#'advent-of-code-2020.day13/read-bus-schedule example-input))))))

(deftest day13-1-example-1-test
  (testing "Day 13 - Part 1 - Example 1"
    (is (= 295 (day13-1
                (#'advent-of-code-2020.day13/read-bus-schedule example-input))))))

(deftest day13-1-test
  (testing "Day 13 - Part 1"
    (is (= 3606 (day13-1)))))

(deftest day13-2-example-1-test
  (testing "Day 13 - Part 2 - Example 1"
    (is (= 1068781
           (day13-2
            (#'advent-of-code-2020.day13/read-bus-schedule example-input))))))

(deftest day13-2-example-3-test
  (testing "Day 13 - Part 2 - Example 3"
    (is (= 754018 (day13-2 [nil [67 7 59 61]])))))

(deftest day13-2-example-4-test
  (testing "Day 13 - Part 2 - Example 4"
    (is (= 779210 (day13-2 [nil [67 nil 7 59 61]])))))

(deftest day13-2-example-5-test
  (testing "Day 13 - Part 2 - Example 5"
    (is (= 1261476 (day13-2 [nil [67 7 nil 59 61]])))))

(deftest day13-2-example-6-test
  (testing "Day 13 - Part 2 - Example 6"
    (is (= 1202161486 (day13-2 [nil [1789 37 47 1889]])))))

(deftest day13-2-test
  (testing "Day 13 - Part 2"
    (is (= 379786358533423 (day13-2)))))