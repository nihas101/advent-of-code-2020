(ns advent-of-code-2020.day22
  (:require
   [clojure.string :as string]))

(defn- read-player [player]
  (let [[p & cards] (string/split-lines
                     (string/replace player #"Player\s+|:" ""))]
    [(Integer/parseInt p) (mapv #(Integer/parseInt %) cards)]))

(defn- read-input [input]
  (into {} (map read-player) (string/split input #"(\r?\n){2}")))

(defn- play-round [{[a & as] 1 [b & bs] 2 :as game-state}]
  (if (< b a)
    (-> game-state
        (assoc ,,, 1 (concat as [a b]))
        (assoc ,,, 2 bs)
        (assoc ,,, :winner 1))
    (-> game-state
        (assoc ,,, 2 (concat bs [b a]))
        (assoc ,,, 1 as)
        (assoc ,,, :winner 2))))

(defn- score [deck]
  (reduce + (mapv * deck (range (count deck) 0 -1))))

(defn- winner [{as 1 bs 2}]
  (if (seq as) as bs))

(defonce ^:private combat
  (read-input (slurp "resources/combat.txt")))

(defn day22-1
  ([] (day22-1 combat))
  ([game-state]
   (->> game-state
        (iterate play-round)
        (drop-while (fn [{as 1 bs 2}] (not (or (empty? as) (empty? bs)))))
        first
        winner
        score)))

(declare play-game)

(defn- play-round-rec [{[a & as] 1 [b & bs] 2 :as game-state}]
  (cond
    ;; Recursive combat
    (and (<= a (count as)) (<= b (count bs)))
    (let [{winner :winner} (play-game (-> game-state
                                          (assoc ,,, 1 (take a as))
                                          (assoc ,,, 2 (take b bs)))
                                      #{})]
      (if (= 1 winner)
        (-> game-state
            (assoc ,,, 1 (concat as [a b]))
            (assoc ,,, 2 bs)
            (assoc ,,, :winner 1))
        (-> game-state
            (assoc ,,, 2 (concat bs [b a]))
            (assoc ,,, 1 as)
            (assoc ,,, :winner 2))))
    ;; Round is played as before
    :else (play-round game-state)))

(defn game-scores [game]
  (mapv score
        ((juxt (fn [g] (get g 1)) (fn [g] (get g 2))) game)))

(defn- play-game [{as 1 bs 2 :as game} previous-games]
  ;; By calculating the game-score we reduce the size of the input
  ;; for the hashing and speed it up
  (let [game-scores (game-scores game)]
    (cond
      (contains? previous-games game-scores) (assoc game :winner 1)
      (or (empty? as) (empty? bs)) game
      :else (recur (play-round-rec game)
                   (conj previous-games game-scores)))))

(defn day22-2
  ([] (day22-2 combat))
  ([game-state]
   (-> game-state
       (play-game #{})
       winner
       score)))