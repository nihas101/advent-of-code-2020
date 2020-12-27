(ns advent-of-code-2020.day07
  (:require
   [clojure.string :as string]
   [clojure.set :as sets]))

(defn- parse-rule [[amount bag]]
  [bag (Long/parseLong amount)])

(defn- parse-bag-rule [[bag contain-rules]]
  [bag (transduce
        (comp
         (map (fn [rule] (string/split rule #"s?\s" 2))) ; Remove trailing \s
         (remove (fn [[_ bag]] (= bag "other bag")))
         (map parse-rule))
        conj
        (string/split contain-rules #"s?,\s"))]) ; Remove trailing \s

(defn- assoc-rules
  ([] {})
  ([rule-map] rule-map)
  ([rule-map [bag rules]]
   (assoc rule-map bag
          (reduce conj {} rules))))

(defn- parse-rules [rules]
  (transduce
   (comp
    (map #(string/split % #"s?\scontain\s")) ; Remove trailing \s
    (map parse-bag-rule))
   assoc-rules
   (string/split rules #"s?\.\r?\n?"))) ; Remove trailing \s

(defn- can-contain-bag [rules-map bag]
  (transduce
   (comp
    (filter (fn [[_ rules]] (contains? rules bag)))
    (map first))
   conj
   rules-map))

(def ^:private aggregate-rules
  (memoize ;; Cache sub-aggregates to speed up later parts of transitive-bag-closure-step
   (fn [rules-map [bag rules]]
     (reduce (fn [r-m b] (update r-m b (partial sets/union rules)))
             rules-map
             (can-contain-bag rules-map bag)))))

(defn- transitive-bag-closure-step [[_ new-rules-map]]
  [new-rules-map (reduce aggregate-rules new-rules-map
                         new-rules-map)])

(def ^:private until-eq-first (comp
                               (drop-while (fn [[a b]] (not= a b)))
                               (map first)))

(defn- rules->set [rules-map]
  (reduce (fn [r-m [b r]] (assoc r-m b (set (keys r)))) rules-map rules-map))

(defn- transitive-bag-closure [rules-map]
  (first (eduction until-eq-first
                   (iterate transitive-bag-closure-step
                            [nil (rules->set rules-map)]))))

(defonce ^:private bag-rules
  (parse-rules (slurp "resources/bag_rules.txt")))

(defn day7-1
  ([] (day7-1 bag-rules))
  ([rules-map]
   (count
    (filter (fn [[_ rules]] (contains? rules "shiny gold bag"))
            (transitive-bag-closure rules-map)))))

(defn- multiply-bag-amount [multiplier bags]
  (map (fn [[bag amount]] [bag (* ^long amount ^long multiplier)]) bags))

(defn- containing-bag-count [rules-map bag]
  (loop [[[b a] & r :as bags] [[bag 1]]
         ;; Do not count the top level bag
         amount (long -1)]
    (if (seq bags)
      (recur (concat r (multiply-bag-amount a (get rules-map b)))
             (+ amount ^long a))
      amount)))

(defn day7-2
  ([] (day7-2 bag-rules))
  ([rules-map]
   (containing-bag-count rules-map "shiny gold bag")))