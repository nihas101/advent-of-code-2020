(ns advent-of-code-2020.day07-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2020.day07 :refer :all]))

(defonce ^:private example-1-rules
  "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")

(deftest day7-1-parse-rules-example-test
  (testing "Day 7 - Part 1 - Example - Parse rules"
    (is (= {"dark orange bag" {"bright white bag" 3, "muted yellow bag" 4}
            "bright white bag" {"shiny gold bag" 1}
            "vibrant plum bag" {"faded blue bag" 5 "dotted black bag" 6}
            "shiny gold bag" {"dark olive bag" 1, "vibrant plum bag" 2}
            "faded blue bag" {}
            "dotted black bag" {}
            "light red bag" {"bright white bag" 1, "muted yellow bag" 2}
            "dark olive bag" {"faded blue bag" 3 "dotted black bag" 4}
            "muted yellow bag" {"shiny gold bag" 2, "faded blue bag" 9}}
           (#'advent-of-code-2020.day07/parse-rules example-1-rules)))))

(deftest day7-1-example-test
  (testing "Day 7 - Part 1 - Example"
    (is (= 4
           (day7-1
            {"dark orange bag" {"bright white bag" 3, "muted yellow bag" 4}
             "bright white bag" {"shiny gold bag" 1}
             "vibrant plum bag" {"faded blue bag" 5 "dotted black bag" 6}
             "shiny gold bag" {"dark olive bag" 1, "vibrant plum bag" 2}
             "faded blue bag" {}
             "dotted black bag" {}
             "light red bag" {"bright white bag" 1, "muted yellow bag" 2}
             "dark olive bag" {"faded blue bag" 3 "dotted black bag" 4}
             "muted yellow bag" {"shiny gold bag" 2, "faded blue bag" 9}})))))

(deftest day7-1-test
  (testing "Day 7 - Part 1"
    (is (= 131 (day7-1)))))

(defonce ^:private example-2-rules
  "shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.")

(deftest day7-2-parse-rules-example-test
  (testing "Day 7 - Part 2 - Example - Parse rules"
    (is (= {"dark blue bag" {"dark violet bag" 2}
            "dark green bag" {"dark blue bag" 2}
            "dark orange bag" {"dark yellow bag" 2}
            "dark red bag" {"dark orange bag" 2}
            "dark violet bag" {}
            "dark yellow bag" {"dark green bag" 2}
            "shiny gold bag" {"dark red bag" 2}}
           (#'advent-of-code-2020.day07/parse-rules example-2-rules)))))

(deftest day7-2-example-test
  (testing "Day 7 - Part 2 - Example"
    (is (= 126
           (day7-2 {"dark blue bag" {"dark violet bag" 2}
                    "dark green bag" {"dark blue bag" 2}
                    "dark orange bag" {"dark yellow bag" 2}
                    "dark red bag" {"dark orange bag" 2}
                    "dark violet bag" {}
                    "dark yellow bag" {"dark green bag" 2}
                    "shiny gold bag" {"dark red bag" 2}})))))

(deftest day7-2-test
  (testing "Day 7 - Part 2 - Example"
    (is (= 11261 (day7-2)))))