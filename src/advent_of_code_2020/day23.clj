(ns advent-of-code-2020.day23
  (:require
   [clojure.string :as string]
   [advent-of-code-2020.utils :as u]))

(defn- read-input [s]
  (let [nums (u/read-longs s #"")]
    ;; Map of previous -> next element
    {:cups
     ;; The cups are an array which indices point to the successors,
     ;; i.e. cups[1]=5 means that 5 is the successor to 1
     (reduce (fn [^longs arr [^long a ^long b]]
               (aset arr a b) arr)
             (long-array (inc (count nums)))
             (conj (partition 2 1 nums) ; Point to the successors
                   [(peek nums) (first nums)])) ; Close the ring
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
  ([{:keys [^longs cups cup] :as state}]
   (let [a (aget cups cup)
         b (aget cups a)
         c (aget cups b)
         aft (aget cups c)
         dest (destination state a b c)
         d (aget cups dest)]
     ;; Reshuffle the cups
     (aset cups dest a)
     (aset cups c d)
     (aset cups cup aft)
     ;; Move on to the next cup
     (assoc state :cup aft)))
  ([^long steps state]
   (if (zero? steps)
     state
     (recur (dec steps) (move state)))))

(defn- state->long [{:keys [^longs cups]}]
  (Long/parseLong
   (string/join
    (eduction
     (comp (map second)
           (take-while (complement #{1})))
     (iterate (fn [[^longs cs i]] [cs (aget cs i)])
              [cups (aget cups 1)])))))

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
    (rest (iterate (fn [[^longs cups ^long cup]] [cups (aget cups cup)])
                   [cups cup])))))

(defn- extend-up-to [{:keys [^long max ^longs cups ^long cup] :as state} ^long up-to]
  (let [lc (last-cup state)
        cups (long-array (inc up-to) cups)]
    ;; Fill the array up with the values up to up-to
    (reduce (fn [^longs cs ^long i] (aset cs i (inc i)) cs)
            cups (range (inc max) up-to))
    ;; Close the ring structure
    (aset cups up-to cup)
    ;; Fix the last cup pointing to the old value
    (aset cups lc (inc max))
    (-> state
        (assoc ,,, :cups cups)
        (assoc ,,, :max up-to))))

(defn day23-2
  ([] (day23-2 (read-input "467528193")))
  ([state]
   (day23-2 (extend-up-to state 1000000) 10000000))
  ([state steps]
   (let [c (:cups (move steps state))
         a ^long (aget ^longs c 1)
         b ^long (aget ^longs c a)]
     (* a b))))