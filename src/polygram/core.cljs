(ns polygram.core
  (:require [polygram.random :refer [rand-no-repeat]]))

;;   Given alphabet A.
;;
;;   `D0L Generate` takes three arguments:
;;   * An axiom  (a member of A)
;;   * A set of context-free rules over A (see description below)
;;   * A number indicating the number of rule applications.
;;
;;   `D1L Generate` takes three arguments:
;;   * An axiom  (a member of A)
;;   * A set of context-dependent rules over A (see description below)
;;   * A number indicating the number of rule applications.
;;
;;   A rule over an alphabet A is either:
;;   * Context-free: A 2-tuple consisting of input (a member of A) and output (a sequence over A).
;;   * Context-dependent: A 2-tuple consisting of an A-check and output (a sequence over A).
;;
;;   An A-check is a function that takes a sequence over A and an index, and returns a boolean value.


(defn applicable-rules
  "Find applicable rules for the unit at index of the term. Provides index and
  term as context to predicate."
  [term index rules]
  (->> rules
    (filter (fn [[pred fn]]
              (pred (term index) index term)))
    (map second)))


(defn apply-rule
  "Apply rule function at index of the term. Provides index and term as context
  to the rule function."
  [rule-fn term index]
  (rule-fn (term index) index term))


(defn apply-rand-rule
  "Apply random rule from rules at index of the term. Returns nil if no rule was
  found for the index of the term."
  [term index rules]
  (let [rule-fns (applicable-rules term index rules)]
    (when (not (empty? rule-fns))
      (apply-rule (rand-nth rule-fns) term index))))


(defn step-all
  "Runs random applicable rule on each unit of the term."
  [term rules]
  (->> term
    (map-indexed #(or (apply-rand-rule term %1 rules) %2))
    (flatten)
    (vec)))


(defn step-one
  "Runs random applicable rule on index returned by marker-fn. Repeatedly calls
  the marker-fn until a rule can be applied for the unit at the index returned."
  [term rules marker-fn]
  (when-let [index (marker-fn term rules)]
    (if-let [applied (apply-rand-rule term index rules)]
      (->> applied
        (assoc term index)
        (flatten)
        (vec))
      (recur term rules marker-fn))))


(defn step-index
  "Creates stepper to run rule at index returned by marker function. The marker
  function is called with the term and the rules."
  [marker-fn]
  #(step-one %1 %2 marker-fn))


(def step-random
  "Runs random applicable rule (if any) at random index."
  (step-index (fn [term _]
                (rand-no-repeat (count term)))))


(defn grow
  "Lazily grows an axiom by repeatedly applying rules to units of the axiom."
  ([axiom rules] (grow axiom rules step-all))
  ([axiom rules step-fn]
   (lazy-seq
     (let [term (step-fn axiom rules)]
       (cons term
             (grow term rules step-fn))))))
