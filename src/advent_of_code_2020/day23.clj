(ns advent-of-code-2020.day23
  (:require
   [clojure.string :as string]
   [advent-of-code-2020.utils :as u]))

(defn- read-input [s]
  (let [nums (u/read-longs s #"")]
    ;; Map of previous -> next element
    {:cups
     (reduce (fn [m [a b]] (assoc m a b))
             {} (conj (partition 2 1 nums) [(peek nums) (first nums)]))
     :cup (first nums)
     :min (reduce min nums)
     :max (reduce max nums)}))

(defn- destination [{:keys [^long cup ^long min ^long max]} a b c]
  ;; We write out the possible destination values for a small speed up
  (let [dest (first (remove #{a b c} [(dec cup) (- cup 2) (- cup 3) (- cup 4)]))]
    (if (<= min ^long dest)
      dest
      ;; If the value is lower than the min, it wraps around and we look
      ;; from there
      (first (remove #{a b c} [max (dec max) (- max 2) (- max 3)])))))

(defn- move
  ([{:keys [cups cup] :as state}]
   (let [a (get cups cup)
         b (get cups a)
         c (get cups b)
         aft (get cups c)
         dest (destination state a b c)
         d (get cups dest)]
     (-> state
         (assoc ,,, :cups (-> cups
                              transient
                              (assoc! ,,, dest a)
                              (assoc! ,,, c d)
                              (assoc! ,,, cup aft)
                              persistent!))
         (assoc ,,, :cup aft))))
  ([^long steps state]
   (if (zero? steps)
     state
     (recur (dec steps) (move state)))))

(defn- state->long [{:keys [cups]}]
  (Long/parseLong
   (string/join
    (eduction
     (comp (map second)
           (take-while (complement #{1})))
     (iterate (fn [[s i]] [s (get s i)])
              [cups (get cups 1)])))))

(defn day23-1
  ([] (day23-1 (read-input "467528193")))
  ([state]
   (day23-1 state 100))
  ([state steps]
   (state->long (move steps state))))

(defn- last-cup [{:keys [cups cup]}]
  (last
   (eduction
    (comp (map second)
          (take-while (complement #{cup})))
    (rest (iterate (fn [[cups cup]] [cups (get cups cup)])
                   [cups cup])))))

(defn- extend-up-to [{:keys [^long max cups ^long cup] :as state} ^long up-to]
  (let [lc (last-cup state)]
    (as-> state s
      (assoc s :cups
             (as-> cups c
               (transient c)
               (reduce (fn [s ^long i] (assoc! s i (inc i)))
                       c (range (inc max) up-to))
               (assoc! c up-to cup)
               (assoc! c lc (inc max))
               (persistent! c)))
      (assoc s :max up-to))))

(defn day23-2
  ([] (day23-2 (read-input "467528193")))
  ([state]
   (day23-2 (extend-up-to state 1000000) 10000000))
  ([state steps]
   (let [c (:cups (move steps state))
         a ^long (get c 1)
         b ^long (get c a)]
     (* a b))))