(ns advent-of-code-2020.day02
  (:require
   [clojure.string :as string]))

(defn- parse-password+policy [[a b letter password]]
  {:a (Long/parseLong a)
   :b (Long/parseLong b)
   :letter (first letter) ; String of length 1 to char
   :password password})

(defn- parse-password+policies [password-policies]
  (map (fn [password-policy] (parse-password+policy
                              (string/split password-policy #"\s+|:\s+|-")))
       password-policies))

(defn- policy-fulfilled-1? [{:keys [a b letter password]}]
  (<= a (get (frequencies password) letter 0) b))

(defonce ^:private passwords
  (parse-password+policies
   (string/split-lines (slurp "resources/passwords.txt"))))

(defn day2
  "https://adventofcode.com/2020/day/2"
  ([policy-fulfilled?]
   (day2 policy-fulfilled? passwords))
  ([policy-fulfilled? passwords]
   (count (filter policy-fulfilled? passwords))))

(def day2-1 (partial day2 policy-fulfilled-1?))

(defn- policy-fulfilled-2? [{:keys [^long a ^long b letter password]}]
  (cond
    (< (count password) (dec b)) false
    (= (get password (dec a)) letter) (not= (get password (dec b)) letter)
    :else (= (get password (dec b)) letter)))

(def day2-2 (partial day2 policy-fulfilled-2?))