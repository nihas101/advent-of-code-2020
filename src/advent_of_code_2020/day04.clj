(ns advent-of-code-2020.day04
  (:require
   [clojure.string :as string]))

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

(defn valid-height? [hgt]
  (cond
    ; Inches
    (string/ends-with? hgt "in")
    (<= 59 (read-string (subs hgt 0 (- (count hgt) 2))) 76)
    ; Centimeters
    (string/ends-with? hgt "cm")
    (<= 150 (read-string (subs hgt 0 (- (count hgt) 2))) 193)
    :else false))

(defonce ^:private pid-regex
  (re-pattern
   (string/join "|"
                (mapv (fn [[n m]] (str "0{" n "}[0-9]{" m "}"))
                      [[0 9] [1 8] [2 7] [3 6] [4 5] [5 4] [6 3] [7 2] [8 1]]))))

(defn valid-passport-2? [{:keys [byr iyr eyr hgt hcl ecl pid] :as passport}]
  (and (empty? (remove (set (keys passport)) required-keys))
       (and byr (<= 1920 (read-string byr) 2002))
       (and iyr (<= 2010 (read-string iyr) 2020))
       (and eyr (<= 2020 (read-string eyr) 2030))
       (and hgt (valid-height? hgt))
       (and hcl (= (re-matches #"#[0-9a-f]{6}" hcl) hcl))
       (and ecl (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))
       (and pid (= (re-matches pid-regex pid) pid))))

(defn day4-2
  "https://adventofcode.com/2020/day/4"
  ([]
   (day4-2 (slurp "resources/passports.txt")))
  ([passport-batch]
   (let [passports (map parse-passport
                        (string/split passport-batch #"\n\n|\r\n\r\n"))]
     (count (filter valid-passport-2? passports)))))