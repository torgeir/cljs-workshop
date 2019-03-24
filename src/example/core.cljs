(ns example.core
  (:require [clojure.pprint :as pp]
            [goog.dom :as dom]
            [quil.core :as q]
            [quil.middleware :as m]))

(enable-console-print!)

(defonce sketch (dom/getElement "sketch"))

(def w 1200)
(def h 800)
(def ndim 150)
(def size 500)

(defn particle [] {:x (* w (Math/random)) :y (* h (Math/random)) :vx 0 :vy 0 :adir 0})
(defn particles [n] (map (fn [] (particle)) (range n)))

(defn update-pos [curr delta max] (mod (+ curr delta) max))
(defn update-vel [curr delta] (q/norm (+ curr delta) 0 2))
(defn update-acc [curr x y] (q/map-range (q/noise (/ x ndim) (/ y ndim)) 0 1 (- Math/PI) Math/PI))

(defn draw [s]
  (q/fill 255 0 0 25)
  (doseq [pnt s] (q/ellipse (:x pnt) (:y pnt) 1 1)))

(defn update-particle [p]
  (assoc p
         :x  (update-pos (:x p) (:vx p) w)
         :y  (update-pos (:y p) (:vy p) h)
         :vx (update-vel (:vx p) (Math/cos (:adir p)))
         :vy (update-vel (:vx p) (Math/sin (:adir p)))
         :adir (update-acc (:adir p) (:x p) (:y p))))

(defn create []
  (q/defsketch perlin-flow
    :host sketch
    :size [w h]
    :setup (fn []
             (q/no-stroke)
             (q/background 20)
             (particles size))
    :update (fn [s] (map update-particle s))
    :draw draw
    :middleware [m/fun-mode]))
