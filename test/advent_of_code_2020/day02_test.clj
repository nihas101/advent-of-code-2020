(ns advent-of-code-2020.day02-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day02 :refer :all]))

(deftest day2-1-parse-password+policy-example-test
  (testing "Parsing the passwords and policies in the example"
    (is (= [{:a 1 :b 3 :letter \a, :password "abcde"}
            {:a 1 :b 3 :letter \b, :password "cdefg"}
            {:a 2 :b 9 :letter \c, :password "ccccccccc"}]
           (#'advent-of-code-2020.day02/parse-password+policies
            ["1-3 a: abcde"
             "1-3 b: cdefg"
             "2-9 c: ccccccccc"])))))

(deftest day2-1-example-test
  (testing "Day 2 - Part 1 - Example"
    (is (= 2 (day2-1
              [{:a 1 :b 3 :letter \a, :password "abcde"}
               {:a 1 :b 3 :letter \b, :password "cdefg"}
               {:a 2 :b 9 :letter \c, :password "ccccccccc"}])))))

(deftest day2-1-test
  (testing "Day 2 - Part 1"
    (is (= 439 (day2-1)))))

(deftest day2-2-example-test
  (testing "Day 2 - Part 2 - Example"
    (is (= 1 (day2-2
              [{:a 1 :b 3 :letter \a, :password "abcde"}
               {:a 1 :b 3 :letter \b, :password "cdefg"}
               {:a 2 :b 9 :letter \c, :password "ccccccccc"}])))))

(deftest day2-2-test
  (testing "Day 2 - Part 2"
    (is (= 584 (day2-2)))))