(ns advent-of-code-2020.day15)

(defonce ^:private init-numbers [2 0 6 12 1 3])

(defn- next-number
  "Produces the next number in the game from the provided `numbers` and
   `n` and updates `numbers` in preparation for the next round."
  [numbers ^long current-time ^long n]
  (if-let [last-time ^long (get numbers n)]
    [(assoc numbers n current-time) (- current-time last-time)]
    [(assoc numbers n current-time) 0]))

(defn- game-setup
  "Sets up the game of the elves"
  [init]
  (reduce (fn [n [i ct]] (assoc n i ct)) {} (mapv vector (butlast init)
                                                  (rest (range)))))

(defn- next-number!
  "Like next-number but operates on arrays"
  [^longs numbers ^long current-time ^long n]
  (let [last-time ^long (aget numbers n)]
    (if (pos? last-time)
      (do (aset numbers n current-time) [numbers (- current-time last-time)])
      (do (aset numbers n current-time) [numbers 0]))))

(defn- game-setup!
  "Like game-setup but creates an array"
  [^long turns init]
  (let [len (inc ^long (reduce max (conj init turns)))
        arr (long-array len)]
    (doseq [[^long n ^long ct] (mapv vector (butlast init) (rest (range)))]
      (aset arr n ct))
    arr))

(defn- day15 [init-numbers ^long turns setup next]
  (let [beg (count init-numbers)
        end (dec (+ beg (- turns beg)))]
    (loop [numbers (setup init-numbers)
           ct beg
           n (peek init-numbers)]
      (let [[new-numbers new-n] (next numbers ct n)]
        (if (< ct end)
          (recur new-numbers (inc ct) new-n)
          new-n)))))

(defn day15-1
  "Uses a clojure hash-map to calculate the last number of the game."
  ([] (day15 init-numbers 2020 game-setup next-number))
  ([turns] (day15 init-numbers turns game-setup next-number))
  ([init-numbers turns] (day15 init-numbers turns game-setup next-number)))

(defn day15-2
  "Uses an array to calculate the last number of the game."
  ([]
   (day15 init-numbers 30000000 (partial game-setup! 30000000) next-number!))
  ([turns]
   (day15 init-numbers turns (partial game-setup! turns) next-number!))
  ([init-numbers turns]
   (day15 init-numbers turns (partial game-setup! turns) next-number!)))