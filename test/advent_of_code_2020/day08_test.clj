(ns advent-of-code-2020.day08-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day08 :refer :all]))

(defonce ^:private example-input
  "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6")

(deftest day8-1-parse-boot-code-example-test
  (testing "Day 8 - Part 1 - Example - Parse boot code"
    (is (= [[:nop 0]
            [:acc 1]
            [:jmp 4]
            [:acc 3]
            [:jmp -3]
            [:acc -99]
            [:acc 1]
            [:jmp -4]
            [:acc 6]]
           (#'advent-of-code-2020.day08/parse-boot-code example-input)))))

(deftest day8-1-example-test
  (testing "Day 8 - Part 1 - Example - Parse boot code"
    (is (= 5 (day8-1 [[:nop 0]
                      [:acc 1]
                      [:jmp 4]
                      [:acc 3]
                      [:jmp -3]
                      [:acc -99]
                      [:acc 1]
                      [:jmp -4]
                      [:acc 6]])))))

(deftest day8-1-test
  (testing "Day 8 - Part 1"
    (is (= 1489 (day8-1)))))

(deftest day8-2-modify-example-test
  (testing "Day 8 - Part 2 - Example - Modify"
    (is (= {:boot-code [[:jmp 0] [:acc 1] [:jmp 4] [:acc 3] [:jmp -3] [:acc -99]
                        [:acc 1] [:jmp -4] [:acc 6]]
            :ip 0
            :acc 0
            :visited #{}
            :modified true}
           (#'advent-of-code-2020.day08/modify-instruction
            {:boot-code [[:nop 0] [:acc 1] [:jmp 4] [:acc 3] [:jmp -3] [:acc -99]
                         [:acc 1] [:jmp -4] [:acc 6]]
             :ip 0
             :acc 0
             :visited #{}})))))

(deftest day8-2-no-modify-example-test
  (testing "Day 8 - Part 2 - Example - No Modify"
    (let [state {:boot-code [[:nop 0] [:acc 1] [:jmp 4] [:acc 3] [:jmp -3] [:acc -99]
                             [:acc 1] [:jmp -4] [:acc 6]]
                 :ip 1
                 :acc 0
                 :visited #{}
                 :modified true}]
      (is (= state
             (#'advent-of-code-2020.day08/modify-instruction state))))))

(deftest day8-2-example-test
  (testing "Day 8 - Part 2 - Example"
    (is (= 8 (day8-2 [[:nop 0]
                      [:acc 1]
                      [:jmp 4]
                      [:acc 3]
                      [:jmp -3]
                      [:acc -99]
                      [:acc 1]
                      [:jmp -4]
                      [:acc 6]])))))

(deftest day8-2-test
  (testing "Day 8 - Part 2"
    (is (= 1539 (day8-2)))))