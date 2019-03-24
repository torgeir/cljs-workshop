(ns example.core
  (:require [goog.dom :as dom]))


(enable-console-print!)


(defonce body (dom/getElement "app"))
(prn body)


(set! (.-backgroundColor (.-style body)) "gray")
;; (set! (.-backgroundColor (.-style body)) "green")