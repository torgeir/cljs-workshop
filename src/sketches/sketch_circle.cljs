(ns sketches.sketch-circle
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [find-palette]]))


;; A2
(def w 1240)
(def h 1754)
(def palette (find-palette "saami"))


(defn create-circle [id]
  {:id id
   :x  (* w (rand))
   :y  (* h (rand))
   :vx 1
   :vy 1})


(defn sketch-update
  "Returns the next state to render. Receives the current state as a paramter."
  [state]
  (map (fn [circle]
         (assoc circle
                :x  (+ (:x circle) (:vx circle))
                :y  (+ (:y circle) (:vy circle))
                :vx 1))
       state))


(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update."
  [state]
  (doseq [circle state]
    (apply q/fill (first (:colors palette)))
    (q/ellipse (:x circle)
               (:y circle)
               40
               40)))


(defn sketch-setup
  "Returns the initial state to use for the update-render loop."
  []
  (q/no-stroke)
  (apply q/background (:background palette))
  (for [n (range 1)]
    (create-circle n)))


(defn create
  "Creates a sketch that draws a circle that moves around."
  [canvas]
  (q/defsketch circle
    :host canvas
    :size [w h]
    :settings (fn [] (q/pixel-density 2))
    :setup sketch-setup
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :key-pressed save-image))
