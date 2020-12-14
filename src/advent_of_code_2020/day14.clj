(ns advent-of-code-2020.day14
  (:require
   [clojure.string :as string]))

(defmulti read-instruction (fn [version instr] [version (first (string/split instr #"\s+|\[" 2))]))

(defn read-assign [instr]
  (let [[_ sink v] (string/split instr #"(mem\[)|(\]\s+=\s+)")]
    [:assign (Integer/parseInt sink) (Integer/parseInt v)]))

(defmethod read-instruction [1 "mem"] [_ instr] (read-assign instr))

(defn- mask [m replacement]
  (read-string
   (string/join
    (reduce (fn [msk bit] (conj msk (replacement bit))) ["2r0"] m))))

(defmethod read-instruction [1 "mask"] [_ msk]
  [:mask [(mask (drop 7 msk) {\X \0 \0 \0 \1 \1})
          (mask (drop 7 msk) {\X \1 \0 \0 \1 \1})]])

(defn- read-program [version s]
  (let [program (string/split-lines s)]
    (eduction (map (fn [instr] (read-instruction version instr)))
              program)))

(defmulti execute-program (fn [{:keys [version]} [op]] [version op]))

(defmethod execute-program [1 :mask] [mem [op new-mask]]
  (assoc mem op new-mask))

(defmethod execute-program [1 :assign] [{:keys [mask] :as mem} [_ sink value]]
  (let [[mor mand] mask]
    (assoc-in mem [:mem sink] (-> value
                                  (bit-or mor)
                                  (bit-and mand)))))

(defonce docking-program (slurp "resources/docking_program.txt"))

(defn day14
  ([version] (day14 version (read-program version docking-program)))
  ([version instr]
   (reduce +
           (-> (fn [m i] (execute-program m i))
               (reduce ,,, {:version version} instr)
               :mem
               vals))))

(def day14-1 (partial day14 1))

(defmethod read-instruction [2 "mem"] [_ instr] (read-assign instr))

(defn- generate-masks [msk]
  (let [mor (mask msk {\X \0 \1 \1 \0 \0})
        mand (mask msk {\X \0 \1 \1 \0 \1})]
    (loop [[m & ms :as msk] msk
           res [["2r0"]]]
      (cond
        ;; Convert all masks to longs
        (empty? msk) [mor mand (mapv (fn [r] (read-string (string/join r)))
                                     res)]
        ;; Floating bit
        (= m \X) (recur ms
                        (concat (mapv #(conj % \0) res)
                                (mapv #(conj % \1) res)))
        :else (recur ms (mapv #(conj % m) res))))))

(defmethod read-instruction [2 "mask"] [_ msk]
  [:mask (generate-masks (drop 7 msk))])

(defmethod execute-program [2 :mask] [mem [op new-mask]]
  (assoc mem op new-mask))

(defmethod execute-program [2 :assign] [{:keys [mask] :as mem} [_ sink value]]
  (let [[mor mand floating-bits] mask
        sink (-> sink (bit-and mand) (bit-or mor))]
    (transduce
     (map (fn [f-bits] (bit-or sink f-bits)))
     (fn
       ([m] m)
       ([m s] (assoc-in m [:mem s] value)))
     mem floating-bits)))

(def day14-2 (partial day14 2))