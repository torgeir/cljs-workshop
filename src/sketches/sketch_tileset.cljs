(ns sketches.sketch-tileset
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [palettes find-palette]]))

;; A2
(def w 1240)
(def h 1754)

(def padding-horizontal 110)
(def spacing 0)
(def depth 5)

(def palette (find-palette "ducci_x"))


(defn draw-square [x1 y1 x2 y2 c1]
  (apply q/fill c1)
  (q/rect x1 y1 x2 y2))

(defn draw-diagonal-square [x1 y1 x2 y2 c1 c2 dir]
  (draw-square x1 y1 x2 y2 c1)
  (apply q/fill c2)
  (q/begin-shape :triangles)
  (q/vertex x1 y1)
  (if (= "inc" dir)
    (q/vertex x2 y1)
    (q/vertex x2 y2))
  (q/vertex x1 y2)
  (q/end-shape))

(defn generate-square [depth]
  (if (= depth 0)
    (if (< (rand) .5) \w \b)
    (let [ul     (generate-square (dec depth))
          ur     (generate-square (dec depth))
          dl     (generate-square (dec depth))
          dr     (generate-square (dec depth))
          choose (rand (+ depth 2))]
      (cond
        (< choose 1)   \w ;; <-- comment out these two lines to make squares equal in size
        (< choose 2)   \b ;; <--
        :else          [ul \- ur \/ dl \- dr]))))


(defn draw-segment [cells size]
  ;;(let [sx size sy (q/ceil (/ size 3))]
  (let [sx size sy size]
    (doseq [cell cells]
      (let [cols (shuffle (:colors palette))]
        (case cell
          \w (draw-diagonal-square spacing spacing sx sy (nth cols 0) (nth cols 1) "inc")
          \b (draw-diagonal-square spacing spacing sx sy (nth cols 0) (nth cols 1) "dec")
          ;; \w (draw-square spacing spacing sx sy (nth cols 0))
          ;; \b (draw-square spacing spacing sx sy (nth cols 0))
          \- (q/translate sx 0)
          \/ (q/translate (- sx) sy)
          (draw-segment cell (/ size 2)))))
    (q/translate (- sx) (- sy))))

(defn sketch-draw [state]
  (apply q/background (:background palette))
  (q/translate padding-horizontal padding-horizontal)
  (draw-segment state (- (/ w 2) padding-horizontal)))

(defn sketch-update [state]
  [(generate-square depth) \-
   (generate-square depth) \/
   (generate-square depth) \-
   (generate-square depth) \/
   (generate-square depth) \-
   (generate-square depth)])

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
             (sketch-update []))
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :key-pressed save-image))
