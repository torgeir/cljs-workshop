(ns example.ellipse
  (:require [quil.core :as q]
            [quil.middleware :as m]))


(def size 100)


(defn setup []
  (q/background 255 255 255)
  {:diameter 20})


(defn update-state [state]
  state)


(defn draw [state]
  ;; (q/background 255 255 255)
  (q/ellipse (/ size 2)
             (/ size 2)
             (:diameter state)
             (:diameter state)))


(defn create [canvas]
  (q/defsketch ellipse
    :host canvas
    :size [size size]
    :setup setup
    :update update-state
    :draw draw
    :middleware [m/fun-mode]))