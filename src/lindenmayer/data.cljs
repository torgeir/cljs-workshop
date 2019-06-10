(ns lindenmayer.data
  (:require [clojure.string :as s]
            [polygram.core :refer [grow]]))


(def cool-trees
  "Some cool lindenmayer trees."
  ["F+F[-F]"
   "FF[+F][--FF][-F+F]"
   "F[-FF[+F]]F[+F[+F]]"
   "F[++F[-F]]F[-FF[F]]"
   "F[-F[-F++F]][+F[--F]]F"])


(defn generate
  "Create a string representing the tree to draw."
  ([axiom rule steps]
   (let [f?          #(= "F" %)
         replacement (constantly (seq rule))
         f-rule      [f? replacement]]
     (->> (grow (s/split axiom "") [f-rule])
       (take steps)
       (last)
       (s/join "")))))