(ns advent-of-code-2020.day14
  (:require
   [clojure.string :as string]))

(defmulti read-instruction (fn [instr] (first (string/split instr #"\s+|\[" 2))))

(defmethod read-instruction "mem" [instr]
  (let [[_ sink v] (string/split instr #"(mem\[)|(\]\s+=\s+)")]
    [:assign (Integer/parseInt sink) (reverse (Integer/toBinaryString
                                               (Integer/parseInt v)))]))

(defmethod read-instruction "mask" [mask]
  [:mask (reverse (drop 7 mask))])

(defn- read-program [s]
  (let [program (string/split-lines s)]
    (eduction (map (fn [instr] (read-instruction instr)))
              program)))


(defmulti execute-program (fn [{:keys [version]} [op]] [version op]))

(defmethod execute-program [1 :mask] [mem [op new-mask]]
  (assoc mem op new-mask))

(defmethod execute-program [1 :assign] [{:keys [mask] :as mem} [_ sink value]]
  (assoc-in mem [:mem sink]
            (mapv (fn [m b] (if (= \X m) b m))
                  mask (concat value (repeat \0)))))

(defonce docking-program
  (read-program (slurp "resources/docking_program.txt")))

(defn- read-binary [v]
  (when (seq v)
    (read-string (string/join (concat "2r" (reverse v))))))

(defn day14
  ([version] (day14 version docking-program))
  ([version instr]
   (transduce
    (map read-binary)
    +
    (-> execute-program
        (reduce ,,, {:version version} instr)
        :mem
        vals))))

(def day14-1 (partial day14 1))

(defmethod execute-program [2 :mask] [mem [op new-mask]]
  (assoc mem op new-mask))

(defn- generate-sinks [sink mask]
  (let [sink (reverse (Integer/toBinaryString sink))]
    (loop [[m & ms :as mask] mask
           [curr & postfix] (concat sink (repeat 0))
           res [[]]]
      (cond
        (empty? mask) (mapv read-binary res)
        (= m \X) (recur ms
                        postfix
                        (concat (mapv #(conj % \0) res)
                                (mapv #(conj % \1) res)))
        (= m \0) (recur ms postfix (mapv #(conj % curr) res))
        (= m \1) (recur ms postfix (mapv #(conj % 1) res))))))

(defmethod execute-program [2 :assign] [{:keys [mask] :as mem} [_ sink value]]
  (let [sinks (generate-sinks sink mask)]
    (reduce (fn [m s] (assoc-in m [:mem s] value))
            mem sinks)))

(def day14-2 (partial day14 2))