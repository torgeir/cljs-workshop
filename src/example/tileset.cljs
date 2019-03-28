(ns example.tileset
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def canvas-size 1000)
(def square-size 960)
(def padding (/ (- canvas-size square-size) 2))
(def spacing 1)
(def depth 8)

(defn generate-square [depth]
  (if (= depth 0)
    (if (< (Math/random) .7) \w \b)
    (let [ul (generate-square (- depth 1))
          ur (generate-square (- depth 1))
          dl (generate-square (- depth 1))
          dr (generate-square (- depth 1))
          choose (* (+ depth 2) (Math/random))]
      (cond
        (< choose 0.4) \w
        (< choose 2) \b
        :else [ul \- ur \/ dl \- dr]))))

(defn draw-square [cells size]
  (doseq [cell cells]
    (case cell
      \w ()
      \b (q/rect spacing spacing size size)
      \- (q/translate size 0)
      \/ (q/translate (- size) size)
      (draw-square cell (/ size 2))))
  (q/translate (- size) (- size)))

(defn create [canvas]
  (q/defsketch tileset
    :host canvas
    :size [canvas-size canvas-size]
    :setup (fn []
             (q/no-stroke)
             (q/fill 0)
             (q/rect-mode :corners)
             (q/frame-rate 1)
             (generate-square depth))
    :update (fn [s] (generate-square depth))
    :draw (fn [s]
            (q/background 120 150 140)
            (q/translate padding padding)
            (draw-square s (/ square-size 2)))
    :middleware [m/fun-mode]))