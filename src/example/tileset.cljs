(ns example.tileset
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def w 800)
(def h 800)
(def xdim 8)
(def ydim 8)
(def cxdim (/ w xdim))
(def cydim (/ h ydim))

(defn rpick [arr]
  (get arr (Math/floor (* (count arr) (Math/random)))))

(defn step [c]
  (case (first c)
    \p [(rpick [\b \w (map step [[\q 0] [\q 1] [\q 2] [\q 3]])]) (second c)]
    \q [(rpick [\b \w]) (second c)]))

(defn state [n] (map step (map (fn [a] [\p a]) (range n))))

(defn draw-cells [cells break]
  (doseq [[cell index] cells]
    (if (string? cell)
      (do
        (q/fill (if (= cell \b) 50 150))
        (q/rect 0 0 cxdim cydim))
      (do
        (q/push-matrix)
        (q/scale 0.5)
        (draw-cells cell 2)
        (q/pop-matrix)))
    (if (== 0 (mod (+ 1 index) break))
      (q/translate (- (* (- break 1) cxdim)) cydim)
      (q/translate cxdim 0))))

(defn create-tileset [canvas]
  (q/defsketch tileset
    :host canvas
    :size [w h]
    :setup (fn []
             (q/no-stroke)
             (q/frame-rate 1)
             (q/background 20)
             (state (* xdim ydim)))
    :update (fn [s] (state (* xdim ydim)))
    :draw (fn [s]
            (draw-cells s xdim))
    :middleware [m/fun-mode]))