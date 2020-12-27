(ns advent-of-code-2020.day11
  (:require
   [clojure.string :as string]))

(defn- insert-seats! [layout seats y]
  (reduce (fn [lo [s x]] (assoc! lo [x y] s))
          layout (mapv vector seats (range))))

(defn- read-layout [layout]
  (persistent!
   (reduce (fn [lo [row x]] (insert-seats! lo row x))
           (transient {})
           (mapv vector (string/split-lines layout) (range)))))

(defn chair-layout->string [layout]
  (let [row-size (inc ^long (apply max (mapv first (keys layout))))]
    (transduce
     (comp (map second) ; Get the chair and neighbors
           (map first)  ; Only take the chair
           (partition-all row-size) ; Split back into rows
           (fn stringify [rf]
             (fn ([] (rf))
               ([result] (rf (string/trim result)))
               ([result input]
                (rf result (string/join (conj input \newline)))))))
     str
     (sort-by (fn [[[^long x ^long y] _]] (+ x (* y 10))) layout))))

(defonce ^:private chair-layout
  (read-layout (slurp "resources/chair_layout.txt")))

(defn- chair-freqs [layout ^long x ^long y]
  (frequencies (eduction
                (comp (map (fn [n] (get layout n)))
                      (map first))
                (second (get layout [x y])))))

(defn- neighbor-pos [^long x ^long y]
  (mapv (fn [[^long dx ^long dy]] [(+ x dx) (+ y dy)]) [[-1 -1] [0 -1] [1 -1]
                                                        [-1  0]        [1  0]
                                                        [-1  1] [0  1] [1  1]]))

;; We first calculate all neighbors once and then only lookup those neighbors
(defn- neighbors [layout [^long x ^long y]]
  (if (= \L (get layout [x y]))
    (update layout [x y] (fn [c] [c (neighbor-pos x y)]))
    (update layout [x y] (fn [c] [c nil])))) ; No reason to calc neighbors for the floor

(defn- handle-empty-chair [new-layout layout x y]
  (if (nil? (get (chair-freqs layout x y) \#))
    (assoc! new-layout [x y] [\# (get-in layout [[x y] 1])])
    new-layout))

(defn- handle-occupied-chair [new-layout layout x y tol]
  (if (< ^long tol ^long (get (chair-freqs layout x y) \# 0))
    (assoc! new-layout [x y] [\L (get-in layout [[x y] 1])])
    new-layout))

(defn- game-of-chairs-step [layout chair-pos tol]
  (persistent!
   (first
    (reduce (fn [[n-lo lo] [x y]]
              (if (= (first (get lo [x y])) \L)
                [(handle-empty-chair n-lo lo x y) lo]
                [(handle-occupied-chair n-lo lo x y tol) lo]))
            [(transient layout) layout]
            chair-pos))))

(defn day11 [chair-layout tolerance]
  (loop [new-chair-layout chair-layout
         chair-layout nil
         chair-pos (transduce (comp (remove (fn [[_ [c]]] (= c \.)))
                                    (map first))
                              conj
                              new-chair-layout)]
    (if (= new-chair-layout chair-layout)
      (count (filter (fn [[_ [c]]] (= c \#)) new-chair-layout))
      (recur (game-of-chairs-step new-chair-layout chair-pos tolerance)
             new-chair-layout
             chair-pos))))

(defn day11-1
  ([] (day11-1 chair-layout))
  ([chair-layout]
   (day11 (reduce neighbors chair-layout
                  (keys chair-layout)) 3)))

;; We first calculate all chairs in view once and then only lookup those neighbors
(defn- chair-in-view [[layout ^long x ^long y] [^long dx ^long dy]]
  (let [new-x (+ x dx)
        new-y (+ y dy)
        chair (get layout [new-x new-y])]
    (if (contains? #{\L \# nil} chair)
      (reduced [new-x new-y])
      [layout new-x new-y])))

(defn- in-view-pos [layout ^long x ^long y]
  (mapv (fn [delta]
          (reduce chair-in-view [layout x y] (repeat delta)))
        [[-1 -1] [0 -1] [1 -1]
         [-1  0]        [1  0]
         [-1  1] [0  1] [1  1]]))

(defn- in-view [init-layout layout [^long x ^long y]]
  (if (= \L (get init-layout [x y]))
    (update layout [x y] (fn [c] [c (in-view-pos init-layout x y)]))
    (update layout [x y] (fn [c] [c nil])))); No reason to calc in-view for the floor

(defn day11-2
  ([] (day11-2 chair-layout))
  ([chair-layout]
   (day11 (reduce (partial in-view chair-layout)
                  chair-layout (keys chair-layout)) 4)))