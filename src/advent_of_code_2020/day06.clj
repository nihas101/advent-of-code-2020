(ns advent-of-code-2020.day06
  (:require
   [clojure.string :as string]
   [clojure.set :as sets]))

(defn- distinct-answeres [group]
  (count (distinct (apply concat group))))

(defonce ^:private customs-answers
  (map #(string/split % #"\s+")
       (string/split (slurp "resources/customs_answers.txt") #"(\n\r?){2,}")))

(defn- day6
  "https://adventofcode.com/2020/day/6"
  ([the-count] (day6 the-count customs-answers))
  ([the-count grouped-customs-answers]
   (transduce (map the-count) + grouped-customs-answers)))

(def day6-1 (partial day6 distinct-answeres))

(defn- common-answered [group]
  (count (apply sets/intersection (mapv set group))))

(def day6-2 (partial day6 common-answered))