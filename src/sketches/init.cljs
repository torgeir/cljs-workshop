(ns sketches.init
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]
            [sketches.sketch-perlin-flow :as perlin-flow]
            [sketches.sketch-tileset :as tileset]
            [sketches.sketch-lindenmayer :as lindenmayer]))


(enable-console-print!)


(defonce sketch (dom/getElement "sketch"))


(defn create-sketch
  "Initialize sketch n."
  [n]
  (condp = n
    1 (perlin-flow/create sketch)
    2 (tileset/create sketch)
    3 (lindenmayer/create sketch)))


;; change this number to change
;; what sketch is rendered
(defonce s (create-sketch 1))
