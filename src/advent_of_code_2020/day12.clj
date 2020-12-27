(ns advent-of-code-2020.day12
  (:require
   [clojure.string :as string]))

(defn- parse-instructions [s]
  (mapv (fn [[d & v]] [d (Long/parseLong (string/join v))])
        (string/split-lines s)))

(defonce ^:private direction->delta
  {0 [0 1]
   90 [1 0]
   180 [0 -1]
   270 [-1 0]})

(def ship
  {:direction 90
   :position [0 0]})

(defmulti execute-instruction (fn [{:keys [waypoint]} [d]] [d (nil? waypoint)]))

(defmethod execute-instruction [\N true] [s [_ v]]
  (update-in s [:position 1] (partial + v)))

(defmethod execute-instruction [\S true] [s [_ v]]
  (update-in s [:position 1] (fn [y] (- y v))))

(defmethod execute-instruction [\E true] [s [_ v]]
  (update-in s [:position 0] (partial + v)))

(defmethod execute-instruction [\W true] [s [_ v]]
  (update-in s [:position 0] (fn [x] (- x v))))

(defmethod execute-instruction [\L true] [s [_ v]]
  (update s :direction #(let [new-v (- % v)]
                          (if (neg? new-v)
                            (+ new-v 360)
                            new-v))))

(defmethod execute-instruction [\R true] [s [_ v]]
  (update s :direction #(mod (+ % v) 360)))

(defmethod execute-instruction [\F true] [s [_ v]]
  (update s :position (fn [[x y]]
                        (let [[dx dy] (direction->delta (:direction s))]
                          [(+ x (* dx v)) (+ y (* dy v))]))))

(defn- execute-instructions [s instructions]
  (reduce execute-instruction s instructions))

(defonce ^:private instructions
  (parse-instructions (slurp "resources/ship_instructions.txt")))

(defn abs [x]
  (max x (- x)))

(defn day12
  ([s] (day12 s instructions))
  ([s instr]
   (apply +
          (mapv abs
                (:position (execute-instructions s instr))))))

(defn- relative-rotation [pos rotation]
  (let [[rot times] (if (pos? rotation)
                      [(fn pos-rot [[x y] _] [y (- x)]) (range (quot rotation 90))]
                      [(fn neg-rot [[x y] _] [(- y) x]) (range (quot (- rotation) 90))])]
    (reduce rot pos times)))

(def ship+waypoint
  {:position [0 0]
   :waypoint [10 1]})

(defmethod execute-instruction [\N false] [s+w [_ v]]
  (update-in s+w [:waypoint 1] (partial + v)))

(defmethod execute-instruction [\S false] [s+w [_ v]]
  (update-in s+w [:waypoint 1] (fn [y] (- y v))))

(defmethod execute-instruction [\E false] [s+w [_ v]]
  (update-in s+w [:waypoint 0] (partial + v)))

(defmethod execute-instruction [\W false] [s+w [_ v]]
  (update-in s+w [:waypoint 0] (fn [x] (- x v))))

(defmethod execute-instruction [\L false] [s+w [_ v]]
  (update s+w :waypoint #(relative-rotation % (- v))))

(defmethod execute-instruction [\R false] [s+w [_ v]]
  (update s+w :waypoint #(relative-rotation % v)))

(defmethod execute-instruction [\F false] [s+w [_ v]]
  (update s+w :position
          (fn [[x y]]
            (let [[dx dy] (:waypoint s+w)]
              [(+ x (* dx v)) (+ y (* dy v))]))))
