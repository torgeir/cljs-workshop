(ns example.core
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]
            [example.perlin_flow :as pf]
            [example.tileset :as ts]))


(enable-console-print!)


(defonce sketch (dom/getElement "sketch"))


(defonce s (pf/create sketch))