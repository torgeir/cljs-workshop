(ns sketches.sketch-lindenmayer
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [cljs.core.async :as async :include-macros true]
            [lib.polygram.dom :as dom]
            [lib.polygram.timers :as timers]
            [lib.lindenmayer.data :as lindenmayer.data]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [find-palette palettes]]))


(def body (.-body js/document))
(def w (.-clientWidth body))
(def h (.-clientHeight body))


(def start-height 20)

(def palette (find-palette "hermes"))
(def min-weight 2)
(def max-weight 6)

(def mean-angle 0.35) ;; Change to something between 0 and Math/TWO_PI
(def angle-variance 0.15)
(def mean-length 10)
(def length-variance 6)

(def angle-low (- mean-angle angle-variance))
(def angle-high (+ mean-angle angle-variance))
(def length-low (- mean-length length-variance))
(def length-high (+ mean-length length-variance))


(defn sketch-update
  "Returns the next state to render. Receives the current state as a paramter."
  [{:keys [ops n] :as state}]
  (let [op (first ops)]
    (-> state
      (assoc :first-draw false)
      (assoc :ops (rest ops))
      (assoc :op op)
      (assoc :n (condp = op
                  "[" (* n 0.8)
                  "]" (/ n 0.8)
                  n)))))


(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update."
  [{:keys [first-draw op n]}]
  (q/pop-matrix)
  (if first-draw
    (do
      (q/translate (/ (q/width) 2) (- (q/height) start-height))
      (q/rotate Math/PI))
    (do
      (condp = op
        "F" (do
              (q/stroke-weight (q/random min-weight (* n max-weight)))
              (apply q/stroke (rand-nth (:colors palette)))
              (let [length (q/random length-low length-high)]
                (q/line 0 0 0 length)
                (q/translate 0 length)))
        "-" (q/rotate (- (q/random angle-low angle-high)))
        "+" (q/rotate  (q/random angle-low angle-high))
        "[" (q/push-matrix)
        "]" (q/pop-matrix)
        nil)))
  (q/push-matrix))


(defn sketch-draw-all
  "A variant of sketch-draw that consumes all operations and
  draws their state to the canvas."
  [{:keys [ops]}]
  (apply q/background (:background palette))
  (q/push-matrix)
  (sketch-draw {:op nil :n 1 :first-draw true})
  (doseq [op ops]
    (sketch-draw {:op op :n 1 :first-draw false})))


(defn sketch-setup
  "Returns the initial state to use for the update-render loop."
  []
  (apply q/background (:background palette))
  (q/frame-rate 1000)
  {:first-draw true
   :n          1
   :ops        (lindenmayer.data/generate
                 "F"
                 (lindenmayer.data/cool-trees 1)
                 5)})


(defn create
  "Creates a sketch that draws a lindenmayer tree from a channel of strings, e.g. like FF[+F][--FF][-F+F]."
  [canvas]
  (js/setTimeout
    (fn []
      (q/defsketch lindenmayer
        :host canvas
        :size [w h]
        :middleware [m/fun-mode]
        :settings (fn [] (q/pixel-density 2))
        :setup sketch-setup
        :update sketch-update
        :draw sketch-draw
        :key-pressed save-image))
    1000))

