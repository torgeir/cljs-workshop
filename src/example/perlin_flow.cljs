(ns example.perlin_flow
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [example.palette :refer [find-palette]]
            [example.key-press-handlers :refer [on-key-press]]))

;; A2
(def w 2480)
(def h 3508)


(def palette (find-palette "ducci_h"))
(def noise-dim 350)
(def diameter 10)


(defn particle []
  {:x     (* w (Math/random))
   :y     (* h (Math/random))
   :vx    0
   :vy    0
   :color (rand-nth (:colors palette))
   :adir  0})


(defn particles [n]
  (map (fn [] (particle)) (range n)))


(defn update-pos [curr delta max]
  (mod (+ curr delta) max))


(defn update-vel [curr delta]
  (q/norm (+ curr delta) 0 2))


(defn update-acc [curr x y]
  (q/map-range (q/noise (/ x noise-dim) (/ y noise-dim))
               0
               1
               (- Math/PI)
               Math/PI))


(defn draw [s]
  (doseq [pnt s]
    (apply q/fill (:color pnt))
    (q/ellipse (:x pnt) (:y pnt) diameter diameter)))


(defn update-particle [p]
  (assoc p
         :x  (update-pos (:x p) (:vx p) w)
         :y  (update-pos (:y p) (:vy p) h)
         :vx (update-vel (:vx p) (Math/cos (:adir p)))
         :vy (update-vel (:vx p) (Math/sin (:adir p)))
         :adir (update-acc (:adir p) (:x p) (:y p))))


(defn create [canvas]
  (q/defsketch perlin-flow
    :host canvas
    :size [w h]
    :setup (fn []
             (q/no-stroke)
             (q/background (:background palette))
             (particles 1000))
    :update (fn [s] (map update-particle s))
    :draw draw
    :middleware [m/fun-mode]
    :key-pressed (on-key-press canvas)))