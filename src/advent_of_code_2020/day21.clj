(ns advent-of-code-2020.day21
  (:require
   [clojure.string :as string]))

(defn- update-allergen [ingredients allergens allergen]
  (reduce (fn [r i]
            (update-in r [:allergens allergen i] (fnil inc 0)))
          allergens ingredients))

(defn- update-ingredient-freq [allergens ingredient]
  (update-in allergens [:frequencies ingredient] (fnil inc 0)))

(defn read-allergens
  ([] {:allergens {} ;; Which ingredients appear how often with which allergen?
       :frequencies {}}) ;; In how many recipes does the ingredient appear?
  ([result] result)
  ([result [recipe allergens]]
   (let [ingredients (string/split recipe #"\s+")
         allergens (set (string/split allergens #",?\s+"))]
     (as-> result alls
       (reduce (partial update-allergen ingredients) alls allergens)
       ;; Update the times the ingredient appears in a recipe
       (reduce update-ingredient-freq alls ingredients)))))

(defn- read-input [s]
  (transduce
   (map #(string/split % #"\s+\(contains\s+|\)"))
   read-allergens
   (string/split-lines s)))

(defn- sort-allergens+ingredient [[all ingr]]
  [all (sort-by second > ingr)])

(defn- assign-definitive-allergens
  "Assign allergens to ingredients which are most often associated with each other
   and for which no other ingredient appears as often. These must be associated,
   since each allergen only appears in one ingredient."
  [allergens]
  (transduce
   (comp
    ;; Filter out the allergens+ingredients tuples where an association can
    ;; definitively happen
    (filter (fn [[_ [[_ ^long i1a] [_ i2a]]]] (or (nil? i2a) (< ^long i2a i1a))))
    ;; Make the association
    (map (fn [[all [[ingr]]]] [all ingr])))
   conj
   allergens))

(defn- remove-allergens+ingredients
  "Removes the ingredients that were assigned from the lists of
   ingredients possibly associated with an allergen. Additionally removes
   the assigned allergens from the list."
  [allergens assigned-all assigned-ingr]
  (transduce
   (comp
    (remove (fn [[all]] (contains? assigned-all all)))
    (map (fn [[all ingrs]]
           [all (remove (fn [[i _]] (contains? assigned-ingr i)) ingrs)])))
   conj
   allergens))

(defn- assign-allergens [allergens-map]
  (loop [;; Sort the ingredients by their frequency once, so that the most
         ;; frequent occurring one can be efficiently found
         allergens (mapv sort-allergens+ingredient allergens-map)
         ;; Remember which ingredients were not yet assigned for part 1
         ingredients (transduce (comp (mapcat keys) (distinct)) conj [] (vals allergens-map))
         assignment {}]
    (let [assign (assign-definitive-allergens allergens)
          assigned-ingr (transduce (map second) conj #{} assign)
          assigned-all (transduce (map first) conj #{} assign)]
      (if (empty? assign)
        [assignment ;; Assigned 
         allergens ;; Unresolved
         ingredients] ;; Ingredients that were not assigned
        (recur (remove-allergens+ingredients allergens assigned-all assigned-ingr)
               (remove assigned-ingr ingredients)
               (reduce conj assignment assign))))))

(defonce ^:private allergens
  (read-input (slurp "resources/allergens.txt")))

(defn day21-1
  ([] (day21-1 allergens))
  ([{:keys [allergens frequencies]}]
   (let [[_ unresolved ingredients] (assign-allergens allergens)
         unresolved-ingredients (transduce (comp (mapcat second) (map first))
                                           conj #{} unresolved)]
     ;; The ingredients which were not assigned and do not appear in
     ;; the unresolved allergen assignments definitely do not contain
     ;; allergens
     (transduce
      (comp
       (remove unresolved-ingredients)
       (map (fn [ingr] (get frequencies ingr))))
      +
      ingredients))))

(defn day21-2
  ([] (day21-2 allergens))
  ([{:keys [allergens]}]
   (let [[assigned] (assign-allergens allergens)]
     (string/join "," (mapv second (sort-by first assigned))))))