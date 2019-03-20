(ns example.core
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]))


(enable-console-print!)


(defonce body (dom/getElement "app"))
(prn body)


(pp/pprint
  (for [x (range 0 4)
        y (range 0 4)]
    [x y]))