(ns sketches.init
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]
            [sketches.sketch-lines :as lines]
            [sketches.sketch-circle :as circle]
            [sketches.sketch-perlin-flow :as perlin-flow]
            [sketches.sketch-tileset :as tileset]
            [sketches.sketch-lindenmayer :as lindenmayer]))


(enable-console-print!)


(defonce sketch (dom/getElement "sketch"))


(defn create-sketch
  "Initialize sketch n."
  [n]
  (condp = n
    0 (circle/create sketch)
    1 (lines/create sketch)
    2 (perlin-flow/create sketch)
    3 (tileset/create sketch)
    4 (lindenmayer/create sketch)))


;; change this number to change
;; what sketch is rendered
(defonce s (create-sketch 0))
