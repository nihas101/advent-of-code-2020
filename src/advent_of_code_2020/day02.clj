(ns advent-of-code-2020.day02
  (:require
   [clojure.string :as string]))

(defn- parse-password+policy [[a b letter password]]
  {:a (Integer/parseInt a)
   :b (Integer/parseInt b)
   :letter (first letter) ; String of length 1 to char
   :password password})

(defn- parse-password+policies [password-policies]
  (map (fn [password-policy] (parse-password+policy
                              (string/split password-policy #"\s+|:\s+|-")))
       password-policies))

(defn- policy-fulfilled-1? [{:keys [a b letter password]}]
  (<= a (get (frequencies password) letter 0) b))

(defn day2
  "https://adventofcode.com/2020/day/2"
  [policy-fulfilled?]
  (fn day2-fn
    ([]
     (let [password-policies (string/split-lines (slurp "resources/passwords.txt"))
           p (parse-password+policies password-policies)]
       (day2-fn p)))
    ([p]
     (count (filter policy-fulfilled? p)))))

(def day2-1 (day2 policy-fulfilled-1?))

(defn- policy-fulfilled-2? [{:keys [a b letter password]}]
  (cond
    (< (count password) (dec b)) false
    (= (get password (dec a)) letter) (not= (get password (dec b)) letter)
    :else (= (get password (dec b)) letter)))

(def day2-2 (day2 policy-fulfilled-2?))