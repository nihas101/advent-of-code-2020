(ns advent-of-code-2020.day13
  (:require
   [advent-of-code-2020.utils :as u]
   [clojure.string :as string]))

(defn- read-bus-schedule [bus-schedule]
  (-> bus-schedule
      string/split-lines
      vec
      (update ,,, 0 #(Long/parseLong %))
      (update ,,, 1 (fn [s] (transduce
                             (map (fn [i]
                                    (when (not= i "x")
                                      (Long/parseLong i))))
                             conj
                             (string/split s #","))))))

(defn- following-time-stamp [time-stamp bid]
  (if (or (nil? bid) (< ^long time-stamp ^long bid))
    bid
    (* ^long bid (inc (quot ^long time-stamp ^long bid)))))

(defn- following-time-stamps [time-stamp bus-ids]
  (mapv (partial following-time-stamp time-stamp) bus-ids))

(defn- bus-schedule+minutes-to-wait [[departure bus-ids]]
  (let [id+wait-time (mapv (fn [bid fts]
                             (if (nil? bid)
                               [bid fts]
                               [bid (mod fts departure)]))
                           bus-ids
                           (following-time-stamps departure bus-ids))]
    [departure (sort-by second id+wait-time)]))

(defonce ^:private bus-schedule
  (read-bus-schedule (slurp "resources/bus_schedule.txt")))

(defn day13-1
  ([] (day13-1 bus-schedule))
  ([bus-schedule]
   (-> bus-schedule
       bus-schedule+minutes-to-wait
       second
       ;; Drop the xs, because we don't care about them for part 1
       ((partial drop-while #(nil? (first %))))
       first
       ((fn [[^long id ^long min-to-wait]] (* id min-to-wait))))))

(defn day13-2
  ([] (day13-2 bus-schedule))
  ([bus-schedule]
   (-> bus-schedule
       second
       ((partial mapv vector (range)))
       ;; Filter the xs, because we don't them at this point anymore
       ((partial remove #(nil? (second %))))
       u/crt)))

;; This was my initial solution for part 2, which wasn't performant enough for
;; the real input
(comment
  (defn- previous-time-stamp [time-stamp bid]
    (if (or (nil? bid) (< time-stamp bid))
      bid
      (* bid (quot (dec time-stamp) bid))))

  (defn- previous-time-stamps [time-stamp bus-ids]
    (mapv (partial previous-time-stamp time-stamp) bus-ids))

  (defn- inc? [[a b & cs :as c]]
    (cond
      (= 1 (count c)) true
       ;; We skip the xs
      (nil? a) (recur (rest c))
       ;; We act like the xs are increasing if we encounter one
      (nil? b) (recur (cons (inc a) cs))
      (= (inc a) b) (recur (rest c))
      :else false))

  (defn- earliest-inc-departure [bus-ids]
    (let [last-ts-idx (dec (count bus-ids))]
      (loop [ts (previous-time-stamps (reduce max (remove nil? bus-ids)) bus-ids)]
        (if (inc? ts)
          ts
          (let [fts (update ts last-ts-idx (fn [t] (following-time-stamp t (get bus-ids last-ts-idx))))]
            (recur (reduce
                    (fn [ft i] (assoc ft i
                                      (previous-time-stamp (get fts last-ts-idx) (get bus-ids i))))
                    fts
                    (range last-ts-idx))))))))

  (defn day13-2
    ([] (day13-2 bus-schedule))
    ([bus-schedule]
     (-> bus-schedule
         second
         earliest-inc-departure
         ((partial remove nil?))
         ((partial reduce min))))))