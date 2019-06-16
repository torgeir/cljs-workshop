(ns sketches.sketch-perlin-flow
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [on-key-press]]
            [sketches.palette :refer [find-palette]]))

;; A2
(def w 2480)
(def h 3508)


(def palette (find-palette "ducci_h"))
(def noise-dim 350)
(def diameter 10)
(def angle 3)

(def particle_uniqueness 0.05) ;; Low number gives similar movement among particles
(defn particle [index]
  {:id    index
   :x     (* w (rand))
   :y     (* h (rand))
   :vx    0
   :vy    0
   :color-index (rand-int (count (:colors palette)))
   :adir  0})


(defn particles [n]
  (map (fn [i] (particle i)) (range n)))


(defn update-pos [curr delta max]
  (mod (+ curr delta) max))


(defn update-vel [curr delta]
  (/ (+ curr delta) 2))


(defn update-acc [x y id]
  (*
   (+
    (q/noise (* x noise-dim) (* y noise-dim))
    (* (q/noise (* x noise-dim) (* y noise-dim) id) particle_uniqueness))
   (* angle Math/PI)))


(defn sketch-update [state]
  (map (fn [p]
         (assoc p
                :x  (update-pos (:x p) (:vx p) w)
                :y  (update-pos (:y p) (:vy p) h)
                :vx (update-vel (:vx p) (Math/cos (:adir p)))
                :vy (update-vel (:vx p) (Math/sin (:adir p)))
                :adir (update-acc (:x p) (:y p) (:id p))))
       state))

(defn sketch-draw [state]
  (doseq [pnt state]
    (apply q/fill (nth (:colors palette) (:color-index pnt)))
    (q/ellipse (:x pnt) (:y pnt) diameter diameter)))

(defn save-image [state]
  (when (= "s" (q/raw-key))
    (q/save (str (js/prompt "Enter name of the sketch to save:") ".jpeg")))
  state)


(defn create [canvas]
  (q/defsketch perlin-flow
    :host canvas
    :size [w h]
    :setup (fn []
             (q/no-stroke)
             (apply q/background (:background palette))
             (particles 1000))
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :key-pressed save-image))
