(ns sketches.sketch-lindenmayer
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [cljs.core.async :as async :include-macros true]
            [lib.polygram.dom :as dom]
            [lib.polygram.timers :as timers]
            [lib.lindenmayer.data :as lindenmayer.data]
            [sketches.palette :refer [find-palette palettes]]))


;; A2
(def w 1240)
(def h 1754)
(def start_height 200)

(def palette (find-palette "hermes"))
(def min-weight 2)
(def max-weight 6)

(def mean_angle 0.35) ;; Change to something between 0 and Math/TWO_PI
(def angle_variance 0.15)
(def mean_length 15)
(def length_variance 6)

(def angle_low (- mean_angle angle_variance))
(def angle_high (+ mean_angle angle_variance))
(def length_low (- mean_length length_variance))
(def length_high (+ mean_length length_variance))

(defn sketch-draw [{:keys [w h op n]}]
  (q/pop-matrix)
  (if-not op
    (do
      (q/translate (/ (q/width) 2) (- (q/height) start_height))
      (q/rotate Math/PI))
    (do
      (condp = op
        "F" (do
              (q/stroke-weight (q/random min-weight (* n max-weight)))
              (apply q/stroke (rand-nth (:colors palette)))
              (let [length (q/random length_low length_high)]
                (q/line 0 0 0 length)
                (q/translate 0 length)))
        "-" (q/rotate (- (q/random angle_low angle_high)))
        "+" (q/rotate  (q/random angle_low angle_high))
        "[" (q/push-matrix)
        "]" (q/pop-matrix)
        nil)))
  (q/push-matrix))


(defn sketch-update [{:keys [chan n] :as state}]
  (let [op (async/poll! chan)]
    (-> state
      (assoc :op op)
      (assoc :n (condp = op
                  "[" (* n 0.8)
                  "]" (/ n 0.8)
                  n)))))

(defn save-image [state]
  (when (= "s" (q/raw-key))
    (q/save (str (js/prompt "Enter name of the sketch to save:") ".jpeg")))
  state)

(defn create [canvas]
  (js/setTimeout
    (fn []
      (q/sketch
        :host canvas
        :size [w h]
        :middleware [m/fun-mode]
        :setup (fn []
                 (q/frame-rate 100)
                 (apply q/background (:background palette))
                 {:n    1
                  :w    w
                  :h    h
                  :chan (async/to-chan
                          (lindenmayer.data/generate
                            "F"
                            (lindenmayer.data/cool-trees 1)
                            5))})
        :update #'sketch-update
        :draw #'sketch-draw
      :settings (fn [] (q/pixel-density 2))
      :key-pressed save-image)) 1000))


