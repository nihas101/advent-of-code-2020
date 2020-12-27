(ns advent-of-code-2020.day16
  (:require
   [advent-of-code-2020.utils :as u]
   [clojure.string :as string]))

(defn- read-rules [rules]
  (transduce
   (comp (mapcat (fn [rule] (string/split rule #":\s+|-|\s*or\s")))
         (partition-all 5)
         (map (fn [[c a b d e]] [c [(Long/parseLong a) (Long/parseLong b)]
                                 [(Long/parseLong d) (Long/parseLong e)]])))
   conj
   (string/split-lines rules)))

(defn- read-ticket [ticket] (u/read-longs ticket #","))

(defn- read-input [input]
  (let [[rules my-ticket other-tickets] (u/split-sections input)]
    {:rules (read-rules rules)
     :my-ticket (read-ticket (second (string/split-lines my-ticket)))
     :other-tickets (mapv read-ticket
                          (rest (string/split-lines other-tickets)))}))

(defn- valid-rules [rules v]
  (seq (filter (fn [[_ [a b] [d e]]]
                 (or (<= a v b) (<= d v e))) rules)))

(defn- invalid-values-fn [rules]
  (mapcat (fn [t] (remove (partial valid-rules rules) t))))

(defonce ^:private tickets
  (read-input (slurp "resources/tickets.txt")))

(defn day16-1
  ([] (day16-1 tickets))
  ([{:keys [rules other-tickets]}]
   (transduce
    (invalid-values-fn rules)
    +
    other-tickets)))

(defn- valid-value-per-position [rules tickets]
  (reduce (fn [occ t] (reduce (fn [occ [idx v]]
                                (update occ idx concat
                                        (mapv first (valid-rules rules v))))
                              occ
                              (mapv vector (range) t)))
          {} tickets))

(defn- possible-assignments [occ]
  (reduce (fn [nm [idx vr]]
            (let [fvr (frequencies vr)
                  maxfreq (transduce (map second) max 0 (frequencies vr))]
              (assoc nm ; Create a new map that maps the pos to possible assignments
                     idx (transduce (comp (filter (fn [[_ freq]] (= freq maxfreq)))
                                          (map first))
                                    conj
                                    #{}
                                    fvr))))
          {} occ))

(defn- pick-assignments
  "Selects the assignments where only one choice exists."
  [possible-assignments]
  (eduction (comp (filter (fn [[_ pa]] (= 1 (count pa))))
                  (map (fn [[idx a]] [idx (first a)])))
            possible-assignments))

(defn- remove-choices
  "Remove the choices from all positions it could previously be in."
  [possible-assignments choices]
  (reduce (fn [pa [_ a]]
            (reduce (fn [pa idx] (update pa idx disj a))
                    pa (keys pa)))
          possible-assignments choices))

(defn- determine-assignment [possible-assignments]
  (loop [possible-assignments possible-assignments
         assignment {}]
    (if-let [assigns (seq (pick-assignments possible-assignments))]
      (recur (remove-choices possible-assignments assigns)
             (reduce (fn [ass [idx a]] (assoc ass idx a)) assignment assigns))
      assignment)))

(defn day16-2
  ([] (day16-2 tickets))
  ([{:keys [rules my-ticket other-tickets]}]
   (let [possible-assignment (-> other-tickets
                                 ((partial valid-value-per-position rules))
                                 possible-assignments)]
     (transduce
      (comp (filter #(string/starts-with? (second %) "departure"))
            (map first) ; Get the position
            (map my-ticket)) ; Look the value up in my ticket
      * (determine-assignment possible-assignment)))))