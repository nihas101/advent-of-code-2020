(ns advent-of-code-2020.day21-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day21 :refer :all]))

(defonce ^:private example-input
  "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)")

(deftest day21-1-example-read-input-test
  (testing "Day 21 - Part 1 - example - read-input"
    (is (= {:allergens {"dairy" {"fvjkl" 1 "kfcds" 1 "mxmxvkd" 2
                                 "nhms" 1 "sbzzf" 1 "sqjhc" 1 "trh" 1}
                        "fish" {"kfcds" 1, "mxmxvkd" 2, "nhms" 1, "sbzzf" 1, "sqjhc" 2}
                        "soy" {"fvjkl" 1, "sqjhc" 1}}
            :frequencies {"mxmxvkd" 3, "kfcds" 1, "sqjhc" 3, "nhms" 1
                          "trh" 1, "fvjkl" 2, "sbzzf" 2}}
           (#'advent-of-code-2020.day21/read-input example-input)))))

(deftest day21-1-example-assign-allergens-test
  (testing "Day 21 - Part 1 - example - assign-allergens"
    (is (= [{"dairy" "mxmxvkd", "fish" "sqjhc", "soy" "fvjkl"}
            []
            ["kfcds" "nhms" "sbzzf" "trh"]]
           (#'advent-of-code-2020.day21/assign-allergens
            {"dairy" {"fvjkl" 1 "kfcds" 1 "mxmxvkd" 2
                      "nhms" 1 "sbzzf" 1 "sqjhc" 1 "trh" 1}
             "fish" {"kfcds" 1, "mxmxvkd" 2, "nhms" 1, "sbzzf" 1, "sqjhc" 2}
             "soy" {"fvjkl" 1, "sqjhc" 1}})))))

(deftest day21-1-example-test
  (testing "Day 21 - Part 1 - example"
    (is (= 5
           (day21-1
            {:allergens {"dairy" {"fvjkl" 1 "kfcds" 1 "mxmxvkd" 2
                                  "nhms" 1 "sbzzf" 1 "sqjhc" 1 "trh" 1}
                         "fish" {"kfcds" 1, "mxmxvkd" 2, "nhms" 1, "sbzzf" 1, "sqjhc" 2}
                         "soy" {"fvjkl" 1, "sqjhc" 1}}
             :frequencies {"mxmxvkd" 3, "kfcds" 1, "sqjhc" 3, "nhms" 1
                           "trh" 1, "fvjkl" 2, "sbzzf" 2}})))))

(deftest day21-1-test
  (testing "Day 21 - Part 1"
    (is (= 1815 (day21-1)))))

(deftest day21-2-example-test
  (testing "Day 21 - Part 2 - example"
    (is (= "mxmxvkd,sqjhc,fvjkl"
           (day21-2
            {:allergens {"dairy" {"fvjkl" 1 "kfcds" 1 "mxmxvkd" 2
                                  "nhms" 1 "sbzzf" 1 "sqjhc" 1 "trh" 1}
                         "fish" {"kfcds" 1, "mxmxvkd" 2, "nhms" 1, "sbzzf" 1, "sqjhc" 2}
                         "soy" {"fvjkl" 1, "sqjhc" 1}}
             :frequencies {"mxmxvkd" 3, "kfcds" 1, "sqjhc" 3, "nhms" 1
                           "trh" 1, "fvjkl" 2, "sbzzf" 2}})))))

(deftest day21-2-test
  (testing "Day 21 - Part 2"
    (is (= "kllgt,jrnqx,ljvx,zxstb,gnbxs,mhtc,hfdxb,hbfnkq"
           (day21-2)))))