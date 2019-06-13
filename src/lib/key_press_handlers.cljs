(ns lib.key-press-handlers
  (:require [lib.files :as files]))

(defn on-key-press [canvas]
  (fn [state e]
    (when (= :p (:key e))
      (files/download-jpeg canvas (js/prompt "Enter name of the sketch to save:")))
    state))

