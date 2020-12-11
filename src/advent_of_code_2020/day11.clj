(ns advent-of-code-2020.day11
  (:require
   [clojure.string :as string]))

(defn- insert-seats [layout seats y]
  (reduce (fn [lo [s x]] (assoc lo [x y] s))
          layout (mapv vector seats (range))))

(defn- read-layout [layout]
  (reduce (fn [lo [row x]] (insert-seats lo row x))
          {}
          (mapv vector (string/split-lines layout) (range))))

(defn chair-layout->string [layout]
  (let [row-size (inc ^long (apply max (mapv first (keys layout))))]
    (transduce
     (comp (map second)
           (partition-all row-size)
           (fn stringify [rf]
             (fn ([] (rf))
               ([result] (rf (string/trim result)))
               ([result input]
                (rf result (string/join (conj input \newline)))))))
     str
     (sort-by (fn [[[^long x ^long y] _]] (+ x (* y 10))) layout))))

(defonce ^:private chair-layout
  (read-layout (slurp "resources/chair_layout.txt")))

(defn- neighbouring-freqs [layout ^long x ^long y]
  (frequencies (for [[dx dy] [[-1 -1] [0 -1] [1 -1]
                              [-1  0]        [1  0]
                              [-1  1] [0  1] [1  1]]]
                 (get layout [(+ x dx) (+ y dy)] \.))))

(defn- handle-empty-chair [chair-freqs new-layout layout x y]
  (if (nil? (get (chair-freqs layout x y) \#))
    (assoc new-layout [x y] \#)
    new-layout))

(defn- handle-occupied-chair [chair-freqs new-layout layout x y tol]
  (if (< ^long tol ^long (get (chair-freqs layout x y) \# 0))
    (assoc new-layout [x y] \L)
    new-layout))

(defn- game-of-chairs-step [f layout chair-pos tol]
  (first
   (reduce (fn [[n-lo lo] [x y]]
             (if (= (get lo [x y]) \L)
               [(handle-empty-chair f n-lo lo x y) lo]
               [(handle-occupied-chair f n-lo lo x y tol) lo]))
           [layout layout]
           chair-pos)))

(defn day11 [f chair-layout tolerance]
  (loop [new-chair-layout chair-layout
         chair-layout nil
         chair-pos (transduce (comp (remove (fn [[_ c]] (= c \.)))
                                    (map first))
                              conj
                              new-chair-layout)]
    (if (= new-chair-layout chair-layout)
      (count (filter (fn [[_ c]] (= c \#)) new-chair-layout))
      (recur (game-of-chairs-step f new-chair-layout chair-pos tolerance)
             new-chair-layout
             chair-pos))))

(defn day11-1
  ([] (day11-1 chair-layout))
  ([chair-layout] (day11 neighbouring-freqs chair-layout 3)))

(defn- chair-in-view [[layout ^long x ^long y] [^long dx ^long dy]]
  (let [new-x (+ x dx)
        new-y (+ y dy)
        chair (get layout [new-x new-y])]
    (if (contains? #{\L \# nil} chair)
      (reduced chair)
      [layout new-x new-y])))

(defn- in-view-freqs [layout ^long x ^long y]
  (frequencies
   (mapv (fn [delta]
           (reduce chair-in-view [layout x y] (repeat delta)))
         [[-1 -1] [0 -1] [1 -1]
          [-1  0]        [1  0]
          [-1  1] [0  1] [1  1]])))

(defn day11-2
  ([] (day11-2 chair-layout))
  ([chair-layout] (day11 in-view-freqs chair-layout 4)))