(ns advent-of-code-2020.day09
  (:require
   [clojure.string :as string]))

(defn- add-to-coll [n c]
  (map (partial + n) c))

(defn- preamble-sums [c]
  (loop [[n & c :as cs] c
         res []]
    (if (seq cs)
      (recur c (concat res (add-to-coll n c)))
      res)))

(defn- xmas-step [[preamble _ x [y & ys]]]
  (if x
    (let [new-preamble (concat (rest preamble) [x])]
      [new-preamble (preamble-sums new-preamble) y ys])
    [nil nil nil nil]))

(defn xmas-steps [preamble-length c]
  (let [preamble (take preamble-length c)
        [c & cs] (drop preamble-length c)]
    (iterate xmas-step [preamble (preamble-sums preamble) c cs])))

(defn- invalid-xmas [preamble-length c]
  (nth
   (first
    (drop-while (fn [[_ preamble-sums x _]] (some #{x} preamble-sums))
                (xmas-steps preamble-length c)))
   2))

(defonce ^:private xmas-code
  (map #(Integer/parseInt %)
       (string/split-lines (slurp "resources/xmas_code.txt"))))

(defn day9-1
  ([] (day9-1 25 xmas-code))
  ([preamble-length xmas-code]
   (invalid-xmas preamble-length xmas-code)))

(defn take-while-including
  "Returns a transducer, which returns items from coll until
   (pred item) returns logical false (including the first item
   for which pred was false)."
  [pred]
  (fn [rf]
    (fn
      ([] (rf))
      ([result] (rf result))
      ([result input]
       (if (pred input)
         (rf result input)
         (reduced (rf result input)))))))

(defn- contiguous-sum
  "Returns the first contiguous set from `possibilities` for which the sum equals `sum`."
  [possibilities sum]
  (loop [contiguous (map vector possibilities (rest possibilities))
         possibilities (rest possibilities)]
    (let [cont (drop-while (fn [cont] (not= (reduce + cont) sum)) contiguous)]
      (cond
        (seq cont) (first cont)
        (seq possibilities) (recur (map conj contiguous (rest possibilities))
                                   (rest possibilities))
        :else nil))))

(defn day9-2
  ([] (day9-2 25 xmas-code))
  ([preamble-length xmas-code]
   (let [v (concat (take preamble-length xmas-code)
                   (eduction
                    (comp
                     (take-while-including (fn [[_ preamble-sums x _]]
                                             (some #{x} preamble-sums)))
                     (map #(nth % 2))) ; Get the numbers up to and including the invalid one
                    conj
                    (xmas-steps preamble-length xmas-code)))
         cont (contiguous-sum (butlast v) (last v))]
     (+ (reduce min cont) (reduce max cont)))))