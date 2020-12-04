(ns advent-of-code-2020.day04
  (:require
   [clojure.string :as string]
   [clojure.spec.alpha :as s]))

(defn- parse-passport [passport]
  (reduce (fn [p [k v]] (assoc p (keyword k) v))
          {}
          (mapv (fn [tuple] (string/split tuple #":"))
                (string/split passport #"\s+"))))

;; cid is optional
(defonce ^:private required-keys [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(defn valid-passport-1? [passport]
  (empty? (remove (set (keys passport)) required-keys)))

(defn day4-1
  "https://adventofcode.com/2020/day/4"
  ([]
   (day4-1 (slurp "resources/passports.txt")))
  ([passport-batch]
   (let [passports (map parse-passport
                        (string/split passport-batch #"\n\n|\r\n\r\n"))]
     (count (filter valid-passport-1? passports)))))

(defonce ^:private pid-regex
  (re-pattern
   (string/join "|"
                (mapv (fn [[n m]] (str "0{" n "}[0-9]{" m "}"))
                      [[0 9] [1 8] [2 7] [3 6] [4 5] [5 4] [6 3] [7 2] [8 1]]))))

(s/def ::byr (s/and string? #(<= 1920 (Integer/parseInt %) 2002)))
(s/def ::iyr (s/and string? #(<= 2010 (Integer/parseInt %) 2020)))
(s/def ::eyr (s/and string? #(<= 2020 (Integer/parseInt %) 2030)))
(s/def ::hgt (s/and string?
                    (s/or :in (s/and #(string/ends-with? % "in")
                                     #(<= 59 (Integer/parseInt (subs % 0 (- (count %) 2))) 76))
                          :cm (s/and #(string/ends-with? % "cm")
                                     #(<= 150 (Integer/parseInt (subs % 0 (- (count %) 2))) 193)))))
(s/def ::hcl (s/and string?
                    #(= (re-matches #"#[0-9a-f]{6}" %) %)))
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def ::pid (s/and string?
                    #(= (re-matches pid-regex %) %)))
(s/def ::cid any?)

(s/def ::passport (s/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                          :opt-un [::cid]))

(defn day4-2
  "https://adventofcode.com/2020/day/4"
  ([]
   (day4-2 (slurp "resources/passports.txt")))
  ([passport-batch]
   (let [passports (map parse-passport
                        ;; Split on empty lines
                        (string/split passport-batch #"\n\n|\r\n\r\n"))]
     (count (filter (partial s/valid? ::passport) passports)))))