(ns advent-of-code-2020.utils
  (:require
   [clojure.string :as string]))

;; Math

(defn- abs [^long x] (max x (- x)))

(defn extended-gcd
  "https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm"
  [^long a ^long b]
  (loop [[o-r r] [(long a) (long b)]
         [o-s s] [(long 1) (long 0)]
         [o-t t] [(long 0) (long 1)]]
    (if (zero? r)
      {:x o-s :y o-t
       :gcd o-r
       :quots [t s]}
      (let [q (quot o-r r)]
        (recur [r (- ^long o-r (* ^long q ^long r))]
               [s (- ^long o-s (* ^long q ^long s))]
               [t (- ^long o-t (* ^long q ^long t))])))))

;; The unusual definition of the lcm is so that it can be used within a transducer
(defn lcm
  ([] nil)
  ([x] x)
  ([a b]
   (if (nil? a)
     b
     (quot (* ^long a ^long b) ^long (:gcd (extended-gcd a b))))))

(defn crt
  "https://brilliant.org/wiki/chinese-remainder-theorem/"
  [as+nz]
  (let [as (mapv first as+nz)
        nz (mapv second as+nz)
        N (reduce * nz)
        ys (mapv (partial / N) nz)
        zs (mapv :x (mapv extended-gcd ys nz))]
    (mod (abs (reduce + (mapv * as ys zs))) N)))

;; Strings

(defn split-sections [s]
  (string/split s #"(\r?\n){2,}"))

(def line-endings #"\r?\n")

(defn read-longs [s split-on]
  (mapv #(Long/parseLong %) (string/split s split-on)))