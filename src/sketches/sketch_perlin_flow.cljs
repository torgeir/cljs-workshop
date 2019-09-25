(ns sketches.sketch-perlin-flow
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [find-palette]]))

(def body (.-body js/document))
(def w (.-clientWidth body))
(def h (.-clientHeight body))

(def padding -20)

;; CHANGE THESE
(def palette (find-palette "ducci_q")) ;; Check out palette.cljs
(def opacity 150) ;; [0-255]
(def noise-dim 0.002) ;; Low number gives smooth curves
(def particle-uniqueness 0.2) ;; Low number gives similar movement among particles
(def particle-size 3) ;; Diameter of each particle
(def angle 2) ;; magnitude of angle -- funny to change while drawing
(def speed 2) ;; distance moved each frame


(defn particle [index]
  {:id    index ;; idea: set id equal to color-index?
   :x     (rand w)
   :y     (rand h)
   :vx    0
   :vy    0
   :adir  0
   :color-index (rand-int (count (:colors palette)))})


(defn particles [n]
  (map (fn [i]
         (particle i))
       (range n)))


(defn update-pos [curr delta max]
  (mod (+ curr delta) (- max padding)))


(defn update-vel [curr delta]
  (/ (+ curr delta) 2))


(defn update-acc [x y id]
  (*
    (+
      (q/noise (* x noise-dim) (* y noise-dim))
      (* (q/noise (* x noise-dim) (* y noise-dim) id) particle-uniqueness))
    (* angle Math/PI)))


(defn sketch-update
  "Returns the next state to render. Receives the current state as a paramter."
  [state]
  (map (fn [p]
         (assoc p
                :x  (update-pos (:x p) (* speed (:vx p)) w)
                :y  (update-pos (:y p) (* speed (:vy p)) h)
                :vx (update-vel (:vx p) (Math/cos (:adir p)))
                :vy (update-vel (:vx p) (Math/sin (:adir p)))
                :adir (update-acc (:x p) (:y p) (:id p))))
       state))


(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update."
  [state]
  (doseq [pnt state]
    (apply q/fill (conj (nth (:colors palette) (:color-index pnt)) opacity))
    (q/ellipse
      (+ (:x pnt) padding)
      (+ (:y pnt) padding)
      particle-size
      particle-size)))


(defn sketch-setup
  "Returns the initial state to use for the update-render loop."
  []
  (q/no-stroke)
  (apply q/background (:background palette))
  (particles 1000))


(defn create
  "Creates a sketch that draws flow lines following a perlin noise field."
  [canvas]
  (q/defsketch perlin-flow
    :host canvas
    :size [w h]
    :settings (fn [] (q/pixel-density 2))
    :setup sketch-setup
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :key-pressed save-image))
