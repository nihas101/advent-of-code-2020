(ns advent-of-code-2020.day18
  (:require
   [clojure.string :as string])
  (:import
   [java.io StringReader PushbackReader]))

(defn- reorder
  "Reorders infix notation to prefix, where operations contained in `low-prec`
   are treated with lower precedence than the other operations."
  ([low-prec a] (if (sequential? a)
                  (reorder low-prec
                           (reorder low-prec (first a))
                           (rest a)) a))
  ([low-prec a [f b & es]]
   (let [rb (reorder low-prec b)]
     (cond
       (empty? es) `(~f ~a ~rb)
       (contains? low-prec f) `(~f ~a ~(reorder low-prec rb es))
       :else (recur low-prec `(~f ~a ~rb) es)))))

(defn- read-input [reorder s]
  (with-open [r (PushbackReader. (StringReader. s))]
    (loop [exprs []]
      (let [expr (read r nil :eof)]
        (if (not= expr :eof)
          ;; Keep reading forms
          (recur (conj exprs expr))
          ;; Reorder the forms into clojure code
          (reorder exprs))))))

(defonce ^:private homework
  (string/split-lines (slurp "resources/homework.txt")))

(defn day18-fn [reorder]
  (fn day18
    ([] (day18 homework))
    ([exprs]
     (transduce
      (comp (map (partial read-input reorder))
            (map eval))
      +
      exprs))))

(def day18-1 (day18-fn (partial reorder #{})))

(def day18-2 (day18-fn (partial reorder #{'*}))) ; Delay evaluation of *