(ns advent-of-code-2020.day06
  (:require
   [clojure.string :as string]
   [clojure.set :as sets]))

(defn- distinct-answeres [group]
  (count (distinct (apply concat group))))

(defn- day6
  "https://adventofcode.com/2020/day/6"
  ([the-count] (day6 the-count
                     (map #(string/split % #"\s+")
                          (string/split (slurp "resources/customs_answers.txt") #"(\n\r?){2,}"))))
  ([the-count grouped-customs-answers]
   (reduce + (map the-count grouped-customs-answers))))

(def day6-1 (partial day6 distinct-answeres))

(defn- common-answered [group]
  (count (apply sets/intersection (map set group))))

(def day6-2 (partial day6 common-answered))