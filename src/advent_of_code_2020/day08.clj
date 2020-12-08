(ns advent-of-code-2020.day08
  (:require
   [clojure.string :as string]))

(defn- parse-boot-code [s]
  (transduce
   (comp
    (map #(string/split % #"\s+"))
    (map (fn [[instruction arg]] [(keyword instruction) (Integer/parseInt arg)])))
   conj
   (string/split-lines s)))

(defmulti execute-instruction (fn [{:keys [boot-code ip]}] (first (get boot-code ip))))

(defmethod execute-instruction :nop [vm]
  (update vm :ip inc))

(defmethod execute-instruction :acc [{:keys [boot-code ip] :as vm}]
  (-> vm
      (update ,,, :acc (fnil + 0) (second (get boot-code ip)))
      (update ,,, :ip inc)))

(defmethod execute-instruction :jmp [{:keys [boot-code ip] :as vm}]
  (update vm :ip (fnil + 0) (second (get boot-code ip))))

(defonce ^:private boot-code
  (parse-boot-code (slurp "resources/boot_code.txt")))

(defn day8-1
  ([] (day8-1 boot-code))
  ([boot-code]
   (loop [state {:boot-code boot-code :ip 0 :acc 0 :visited #{}}]
     (if (contains? (:visited state) (:ip state))
       (:acc state)
       (recur (-> state
                  execute-instruction
                  (update ,,, :visited conj (:ip state))))))))

(defn- modify-instruction [{:keys [ip modified] :as state}]
  (if modified ; Only change one :nop or :jmp
    state
    (-> state
        (update-in ,,, [:boot-code ip 0] {:jmp :nop
                                          :nop :jmp
                                          :acc :acc})
        (assoc ,,, :modified true))))

(defn day8-2
  ([] (day8-2 boot-code))
  ([boot-code]
   (loop [[{:keys [boot-code visited ip] :as state} & states :as stack]
          [{:boot-code boot-code :ip 0 :acc 0 :visited #{}}]]
     (cond
       (empty? stack) false
       (contains? visited ip) (recur states)
       (<= (count boot-code) ip) (:acc state)
       ;; Attempt to fix infinite loop
       (#{:jmp :nop} (first (get boot-code ip)))
       (recur
        (conj states
              (-> state
                  modify-instruction
                  execute-instruction
                  (update ,,, :visited conj ip))
              (-> state
                  execute-instruction
                  (update ,,, :visited conj ip))))
       ;; Keep looking
       :else (recur (conj states
                          (-> state
                              execute-instruction
                              (update ,,, :visited conj ip))))))))