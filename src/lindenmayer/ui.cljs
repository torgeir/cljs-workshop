(ns lindenmayer.ui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [cljs.core.async :as async :include-macros true]
            [polygram.dom :as dom]
            [polygram.timers :as timers]
            [lindenmayer.data :as lindenmayer.data]
            [example.palette :refer [find-palette palettes]]))


;; A2
(def w 2481)
(def h 3484.5)


(def palette (find-palette "rag-taj"))


(defn s-draw [{:keys [w h op n first]}]
  (q/pop-matrix)
  (when first
    (q/translate (/ w 2) (/ h 1.2))
    (q/rotate Math/PI))
  (when op
    (condp = op
      "F" (do
            (q/stroke-weight (q/random (* 30 n)))
            (apply q/stroke
                   (rand-nth (:colors palette)))
            (let [l (q/random 1 (* 100 n))]
              (q/line 0 0 0 l)
              (q/translate 0 l)))
      "-" (q/rotate (rand -0.95))
      "+" (q/rotate (rand 0.90))
      "[" (q/push-matrix)
      "]" (q/pop-matrix)
      nil)
    (q/push-matrix)))


(defn s-update [{:keys [chan n] :as s}]
  (let [op (async/poll! chan)]
    (-> s
      (assoc :first false)
      (assoc :op op)
      (assoc :n (condp = op
                  "[" (* n 0.8)
                  "]" (/ n 0.8)
                  n)))))

(defn create [canvas]
  (js/setTimeout
    (fn []
      (q/sketch
        :host canvas
        :size [w h]
        :middleware [m/fun-mode]
        :setup (fn []
                 (q/frame-rate 4000)
                 (apply q/background (:background palette))
                 {:first true
                  :n     1
                  :w     w
                  :h     h
                  :chan  (async/to-chan
                           (lindenmayer.data/generate
                             "F"
                             (lindenmayer.data/cool-trees 1)
                             5))})
        :update #'s-update
        :draw #'s-draw))
    1000))