(ns example.core
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]
            [example.perlin_flow :as perlin_flow]
            [example.tileset :as tileset]
            [lindenmayer.ui :as lindenmayer.ui]))


(enable-console-print!)


(defonce sketch (dom/getElement "sketch"))


(defn create-sketch [n]
  (condp = n
    1 (perlin_flow/create sketch)
    2 (tileset/create sketch)
    3 (lindenmayer.ui/create sketch)))


(defonce s (create-sketch 1))