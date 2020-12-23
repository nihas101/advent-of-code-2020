(ns advent-of-code-2020.day10
  (:require
   [advent-of-code-2020.utils :as u]))

(defonce ^:private adapters
  (u/read-longs (slurp "resources/adapters.txt") u/line-endings))

(defn day10-1
  ([] (day10-1 adapters))
  ([adapters]
   (let [sorted-adapters (sort adapters)]
     (-> (concat (mapv - sorted-adapters (conj sorted-adapters 0)) [3])
         frequencies
         ((juxt #(get % 1) #(get % 3)))
         ((fn [[^long a ^long b]] (* a b)))))))

(defn adapter-combinations
  ([[beg & sorted-adapters]] (adapter-combinations sorted-adapters beg {beg 1}))
  ([[^long curr & sorted-adapters] prev prev-adapter-combs]
   (if (seq sorted-adapters)
     (recur sorted-adapters curr
            (update prev-adapter-combs curr
                    (fnil
                     (fn [^long v]
                      ;; If multiple adapters with the same joltage
                      ;; exist, we can choose to drop any combination, so
                      ;; the possibilities multiply
                       (* v ^long (transduce (map #(get prev-adapter-combs % 0))
                                             ;; An adapter is certain to be in this
                                             ;; range per the problem, so the last
                                             ;; value is always carried along
                                             + (range (- curr 3) (inc curr)))))
                     1)))
     (get prev-adapter-combs prev))))

(defn day10-2
  ([] (day10-2 adapters))
  ([adapters]
   (if (< (count adapters) 2)
     1
     (let [sorted-adapters (sort adapters)]
       (adapter-combinations
        (concat [0] sorted-adapters
                [(+ ^long (last sorted-adapters) (long 3))]))))))