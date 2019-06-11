(ns example.core
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]
            [example.ellipse :as ellipse]
            [example.perlin_flow :as perlin_flow]
            [example.tileset :as tileset]
            [lindenmayer.ui :as lindenmayer.ui]))


(enable-console-print!)


(defonce sketch (dom/getElement "sketch"))


(defonce s (lindenmayer.ui/create sketch))