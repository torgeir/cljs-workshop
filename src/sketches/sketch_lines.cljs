(ns sketches.sketch-lines
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [find-palette]]))


(def body (.-body js/document))
(def w (.-clientWidth body))
(def h (.-clientHeight body))


(def palette {:name "red-and-blue-lazers" :colors [[255 0 60 60]
                                                   [0 60 255 20]]})


(def sides
  [:top :right :bottom :left])


(defn point
  "Creates a random point, on the form of [x y] on a side of the screen."
  [side]
  (condp = side
    :top    [(rand w) 0]
    :right  [w (rand h)]
    :bottom [(rand w) h]
    :left   [0 (rand h)]))


(defn sketch-update
  "Returns the next line to render.."
  []
  (->> sides
    (shuffle)
    (take 2)
    (map point)))


(defn sketch-draw
  "Draws the current line to the canvas. Called on each iteration after sketch-update."
  [line]
  (q/stroke-weight 1)
  (apply q/stroke (rand-nth (:colors palette)))
  (apply q/line line))


(defn sketch-setup
  "Returns the initial state to use for the update-render loop."
  []
  (q/no-stroke)
  (apply q/background [0 0 0])
  [(point :top) (point :bottom)])


(defn create
  "Creates a sketch that draws lines."
  [canvas]
  (q/defsketch lines
    :host canvas
    :size [w h]
    :settings (fn [] (q/pixel-density 2))
    :setup sketch-setup
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :key-pressed save-image))
