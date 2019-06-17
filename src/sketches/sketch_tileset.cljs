(ns sketches.sketch-tileset
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [palettes find-palette]]))

;; A2
(def w 1240)
(def h 1754)

(def padding-horizontal 100)
(def spacing 4)
(def depth 8)

(def palette (find-palette "cc242"))


(defn generate-square [depth]
  (if (= depth 0)
    (if (< (rand) .7) \w \b)
    (let [ul     (generate-square (- depth 1))
          ur     (generate-square (- depth 1))
          dl     (generate-square (- depth 1))
          dr     (generate-square (- depth 1))
          choose (rand (+ depth 2))]
      (cond
        (< choose 0.1) \w
        (< choose 3)   \b
        :else          [ul \- ur \/ dl \- dr]))))


(defn draw-square [cells size]
  (doseq [cell cells]
    (case cell
      \w ()
      \b (do
           (apply q/fill (rand-nth (:colors palette)))
           (q/rect spacing spacing size size))
      \- (q/translate size 0)
      \/ (q/translate (- size) size)
      (draw-square cell (/ size 2))))
  (q/translate (- size) (- size)))


(defn sketch-update [state]
  (generate-square depth))


(defn sketch-draw [state]
  (apply q/background (:background palette))
  (q/translate padding-horizontal (/ (- h
                                        (- w (* 2 padding-horizontal)))
                                     2))
  (draw-square state (- (/ w 2) padding-horizontal)))


(defn create [canvas]
  (q/defsketch tileset
    :host canvas
    :size [w h]
    :settings (fn [] (q/pixel-density 2))
    :setup (fn []
             (q/no-stroke)
             (q/fill 0)
             (q/rect-mode :corners)
             (q/frame-rate 1)
             (generate-square depth))
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :key-pressed save-image))
