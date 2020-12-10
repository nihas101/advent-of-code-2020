(ns advent-of-code-2020.day10
  (:require
   [clojure.string :as string]))

(defonce ^:private adapters
  (map #(Integer/parseInt %)
       (string/split-lines (slurp "resources/adapters.txt"))))

(defn day10-1
  ([] (day10-1 adapters))
  ([adapters]
   (let [sorted-adapters (sort adapters)]
     (-> (concat (mapv - sorted-adapters (conj sorted-adapters 0)) [3])
         frequencies
         ((juxt #(get % 1) #(get % 3)))
         ((fn [[^long a ^long b]] (* a b)))))))

(def adapter-combinations
  (memoize ;; Cache subresults to speed up calculations
   (fn [[a _ c & ads :as sorted-adapters]]
     (cond
       (or (not c)) (long 1)
       (<= (- ^long c ^long a) 3) (+ ^long (adapter-combinations (rest sorted-adapters))
                                     ^long (adapter-combinations (conj ads c a)))
       :else (recur (rest sorted-adapters))))))

(defn day10-2
  ([] (day10-2 adapters))
  ([adapters]
   (if (< (count adapters) 2)
     1
     (let [sorted-adapters (sort adapters)]
       (adapter-combinations
        (concat [0] sorted-adapters [(+ ^long (last sorted-adapters) (long 3))]))))))