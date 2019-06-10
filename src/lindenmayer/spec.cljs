(ns lindenmayer.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.generators :as gen]))


(s/def ::alphabet #{"F"})


(s/def ::operator #{"-" "+"})


(s/def ::group (s/cat
                 :push     #{"["}
                 :children ::rule
                 :pop      #{"]"}))


(s/def ::rule (s/+
                (s/alt :op    ::operator
                       :char  ::alphabet
                       :group ::group)))


(defn create-rule
  "Create a rule of `size` using test.check."
  [size]
  (->> (gen/generate (s/gen ::rule) size)
    (apply str)))


(defn parse-rule
  "Parse a rule to data."
  [rule]
  (s/conform ::rule (seq rule)))