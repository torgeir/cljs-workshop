(ns sketches.sketch-lindenmayer
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [cljs.core.async :as async :include-macros true]
            [lib.polygram.dom :as dom]
            [lib.polygram.timers :as timers]
            [lib.lindenmayer.data :as lindenmayer.data]
            [sketches.palette :refer [find-palette palettes]]))


;; A2
(def w 2480)
(def h 3508)


(def palette (find-palette "rag-taj"))


(defn sketch-draw [{:keys [w h op n]}]
  (q/pop-matrix)
  (if-not op
    (do
      (q/translate (/ w 2) (/ h 1.2))
      (q/rotate Math/PI))
    (do
      (condp = op
        "F" (do
              (q/stroke-weight (q/random (* 30 n)))
              (apply q/stroke
                     (rand-nth (:colors palette)))
              (let [l (q/random 1 (* 100 n))]
                (q/line 0 0 0 l)
                (q/translate 0 l)))
        "-" (q/rotate (rand -0.90))
        "+" (q/rotate (rand 0.90))
        "[" (q/push-matrix)
        "]" (q/pop-matrix)
        nil)
      (q/push-matrix))))


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
      :key-pressed save-image)) 1000))


