(ns advent-of-code-2020.day06
  (:require
   [advent-of-code-2020.utils :as u]
   [clojure.string :as string]
   [clojure.set :as sets]))

(defn- distinct-answers [group]
  (count
   (transduce
    (comp (mapcat identity)
          (distinct))
    conj
    group)))

(defonce ^:private customs-answers
  (mapv #(string/split % #"\s+")
        (u/split-sections (slurp "resources/customs_answers.txt"))))

(defn- day6
  "https://adventofcode.com/2020/day/6"
  ([the-count] (day6 the-count customs-answers))
  ([the-count grouped-customs-answers]
   (transduce (map the-count) + grouped-customs-answers)))

(def day6-1 (partial day6 distinct-answers))

(defn- common-answered [group]
  (count
   (transduce
    (map set)
    (fn ([] nil)
      ([result] result)
      ([result input]
       (if result
         (sets/intersection result input)
         input)))
    group)))

(def day6-2 (partial day6 common-answered))