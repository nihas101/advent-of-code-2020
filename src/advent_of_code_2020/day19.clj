(ns advent-of-code-2020.day19
  (:require
   [clojure.string :as string]
   [instaparse.core :as insta]))

(defonce ^:private rules+messages
  (string/split (slurp "resources/messages.txt") #"(\r?\n){2}"))

(defn day19-1
  ([]
   (let [[rules messages] rules+messages]
     (day19-1 rules (string/split-lines messages))))
  ([rules+messages]
   (let [[rules messages] (string/split rules+messages #"(\r?\n){2}")]
     (day19-1 rules (string/split-lines messages))))
  ([rules messages]
   (transduce
    ;; The real input is not in numerical order, so we have to
    ;; explicitly define rule 0 as starting point
    (comp (map (insta/parser rules :start :0))
          (remove insta/failure?))
    (fn counter
      ([] 0)
      ([result] result)
      ([^long result _] (inc result)))
    messages)))

(defn day19-2
  ([]
   (let [[rules messages] rules+messages]
     (day19-2 rules (string/split-lines messages))))
  ([rules+messages]
   (let [[rules messages] (string/split rules+messages #"(\r?\n){2}")]
     (day19-2 rules (string/split-lines messages))))
  ([rules messages]
   (-> rules
       (string/replace ,,, #"8: 42" "8: 42 | 42 8")
       (string/replace ,,, #"11: 42 31" "11: 42 31 | 42 11 31")
       (day19-1 ,,, messages))))