(ns advent-of-code-2020.day18
  (:require
   [clojure.string :as string]))

(defn- exps->sexps
  "Reorders infix notation to prefix, where operations contained in `low-prec`
   are treated with lower precedence than the other operations."
  ([low-prec aexp] (if (sequential? aexp)
                     (exps->sexps low-prec (exps->sexps low-prec (first aexp))
                                  (rest aexp))
                     aexp))
  ([low-prec asexp [f b & es]]
   (let [bsexp (exps->sexps low-prec b)]
     (cond
       (empty? es) `(~f ~asexp ~bsexp)
       (contains? low-prec f) `(~f ~asexp ~(exps->sexps low-prec bsexp es))
       :else (recur low-prec `(~f ~asexp ~bsexp) es)))))

(defn- read-input [low-prec input]
  (exps->sexps low-prec (read-string (str "(" input ")"))))

(defonce ^:private homework
  (string/split-lines (slurp "resources/homework.txt")))

(defn day18-fn [low-prec]
  (fn day18
    ([] (day18 homework))
    ([exprs]
     (transduce
      (comp (map (partial read-input low-prec))
            (map eval))
      +
      exprs))))

(def day18-1 (day18-fn #{}))

(def day18-2 (day18-fn #{'*})) ; Delay evaluation of *