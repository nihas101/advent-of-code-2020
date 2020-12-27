(ns advent-of-code-2020.day04
  (:require
   [clojure.string :as string]
   [advent-of-code-2020.utils :as u]
   [clojure.spec.alpha :as s]))

(defn- parse-passport [passport]
  (transduce
   (map (fn [tuple] (string/split tuple #":")))
   (fn
     ([] {})
     ([p] p)
     ([p [k v]] (assoc p (keyword k) v)))
   (string/split passport #"\s+")))

(s/def ::passport-1 #(every? (set (keys %))
                             [:byr :iyr :eyr :hgt :hcl :ecl :pid]))

(defonce ^:private passports (slurp "resources/passports.txt"))

(defn day4
  "https://adventofcode.com/2020/day/4"
  ([passport-spec]
   (day4 passport-spec passports))
  ([passport-spec passport-batch]
   (count
    (transduce
     (comp
      (map parse-passport)
      (filter (partial s/valid? passport-spec)))
     conj
     (u/split-sections passport-batch)))))

(def day4-1 (partial day4 ::passport-1))

(defonce ^:private pid-regex
  (re-pattern
   (string/join "|"
                (mapv (fn [[n m]] (str "0{" n "}[0-9]{" m "}"))
                      [[0 9] [1 8] [2 7] [3 6] [4 5] [5 4] [6 3] [7 2] [8 1]]))))

(s/def ::byr (s/and string? #(<= 1920 (Long/parseLong %) 2002)))
(s/def ::iyr (s/and string? #(<= 2010 (Long/parseLong %) 2020)))
(s/def ::eyr (s/and string? #(<= 2020 (Long/parseLong %) 2030)))
(s/def ::hgt (s/and string?
                    (s/or :in (s/and #(string/ends-with? % "in")
                                     #(<= 59 (Long/parseLong (subs % 0 (- (count %) 2))) 76))
                          :cm (s/and #(string/ends-with? % "cm")
                                     #(<= 150 (Long/parseLong (subs % 0 (- (count %) 2))) 193)))))
(s/def ::hcl (s/and string?
                    #(= (re-matches #"#[0-9a-f]{6}" %) %)))
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def ::pid (s/and string?
                    #(= (re-matches pid-regex %) %)))
(s/def ::cid any?)

(s/def ::passport-2 (s/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                            :opt-un [::cid]))

(def day4-2 (partial day4 ::passport-2))