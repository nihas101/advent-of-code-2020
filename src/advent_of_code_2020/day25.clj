(ns advent-of-code-2020.day25)

(defn- transform-step [^long subject-number [^long loop-size ^long x]]
  [(inc loop-size) (-> x
                       (* ,,, subject-number)
                       (rem ,,, 20201227))])

(defn- transform-subject-number
  ([subject-number]
   (iterate (partial transform-step subject-number) [0 1]))
  ([subject-number loop-size]
   (loop [[ls x :as lsx] [0 1]]
     (if (= loop-size ls)
       x
       (recur (transform-step subject-number lsx))))))

(defn- loop-size [public-key subject-number]
  (ffirst
   (drop-while (fn [[_ x]] (not= x public-key))
               (transform-subject-number subject-number))))

(defn- encryption-key [public-key loop-size]
  (transform-subject-number public-key loop-size))

(defonce ^:private public-keys [3469259 13170438])

(defn day25-1
  ([] (apply day25-1 public-keys))
  ([card-pubkey door-pubkey]
   (let [card-ls (loop-size card-pubkey 7)
         door-ls (loop-size door-pubkey 7)]
     [(encryption-key door-pubkey card-ls)
      (encryption-key card-pubkey door-ls)])))