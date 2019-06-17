(ns lib.key-press-handlers
  (:require [quil.core :as q]))


(defn save-image [state e]
  (when (= :s (:key e))
    (q/save (str (js/prompt "Enter name of the sketch to save:") ".jpeg")))
  state)
