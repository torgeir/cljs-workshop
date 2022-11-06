(ns sketches.sketch-circle
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [find-palette]]))


(def body (.-body js/document))
(def w (.-clientWidth body))
(def h (.-clientHeight body))


(def palette (find-palette "saami"))


(defn create-circle [id]
  "Creates a circle.

  Suggestions:
  - random color from the palette per circle
  - random size from a vector of sizes"
  {:id id
   :x  (rand w)
   :y  (rand h)
   :vx 1
   :vy 1})


(defn sketch-update
  "Returns the next state to render. Receives the current state as a paramter.

  Suggestions:
  - use mod to keep circles in view
  - pick a color from the circle state
  - pick a size from the circle state
  - mix up movement along x and y axis, e.g. using (q/noise x y z)"
  [state]
  (map (fn [circle]
         (assoc circle
                :x  (+ (:x circle) (:vx circle))
                :y  (+ (:y circle) (:vy circle))
                :vx 1))
       state))


(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update.

  Suggestions:
  - try to make each circle transparent, e.g. using (conj coll 100) to add an
  alpha channel of 100 to the :color vector of each circle"
  [state]
  (doseq [circle state]
    (apply q/fill (first (:colors palette)))
    (q/ellipse (:x circle)
               (:y circle)
               40
               40)))


(defn sketch-setup
  "Returns the initial state to use for the update-render loop.
  
  Suggestions:
  - change the number of circles by adjusting the range
  - (requires reload as this is only run initially, try evaluating the comment
    block below to reload)"
  []
  (q/no-stroke)
  (apply q/background (:background palette))
  (for [n (range 1)]
    (create-circle n)))

(comment
  (js/location.reload))

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
