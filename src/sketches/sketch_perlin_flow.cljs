(ns sketches.sketch-perlin-flow
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [lib.key-press-handlers :refer [save-image]]
            [sketches.palette :refer [find-palette]]
            [goog.functions :as funcs]
            [goog.events :as events]
            [goog.events.EventType :as EventType]
            ))


(defn map-vals [f m]
  (into {}
        (map (fn [[k v]] [k (f v)])
             (seq m))))


(defn debug [v & [desc]]
  (do
    (println (or
              (and (map? v)
                   (or (and desc (str desc ":")) "")
                   (map-vals (fn [v] (.toFixed v 3)) v))
              (and desc (str desc ": " v))
              v))
    v))


(def window js/window)
(def body (.-body js/document))
(def w (.-clientWidth body))
(def h (.-clientHeight body))
(defn size []
  [(.-clientWidth body)
   (.-clientHeight body)])


(defn shift [[a b c]] [b c a])


(def palette
  {:name       "hazy"
   :background [5 10 15]
   :colors     [[32 0 40]
                [62 15 100]
                [99 53 126]
                [102 0 150]
                [132 16 200]
                [165 22 250]
                [200 66 255]]})


(defn create-shuffle []
  (let [idxs (shuffle (range 3))]
    #(vec (map (fn [items]
             [(nth items (nth idxs 0))
              (nth items (nth idxs 1))
              (nth items (nth idxs 2))])
           %))))

(def rand-shift (create-shuffle))

(def pal (rand-shift (:colors palette)))


(defn particle [index1]
  (let [rand-shift (create-shuffle)]
    (array (rand (q/width))  ; x
           (rand (q/height)) ; y
           0                 ; vx
           0                 ; vy
           0                 ; dir
           (rand-nth pal)))) ; color


(defn particles [n]
  (map particle (range n)))


(defn clamp-sin [v]
  (+ 0.5 (* 0.5 (Math/sin v))))


(defn dec-or-zero [v]
  (if (pos? v) (- v 100) 0))


(defn update-pos [curr delta]
  (+ curr delta))

(defn update-vel [curr dx]
  (/ (+ curr dx) 2))

(defn update-acc [x y z angle noise uniqueness]
  (let [n (q/noise (* x noise) (* y noise))]
    (* (+ n
          (* (q/noise (* x noise) (* y noise) (* z noise))
             uniqueness))
       angle)))

(defn sketch-update
  "Returns the next state to render. Receives the current state as a paramter."
  [{:keys [angle noise speed time uniqueness particles] :as state}]
  (if (zero? (mod time 100))
    (debug (select-keys
            state
            [:time :angle :noise :opacity :size :speed :uniqueness])
           "state"))
  (-> state 
      (update-in [:time] inc)
      (update-in [:clear] dec-or-zero)
      (update-in [:uniqueness] identity)
      (assoc :particles
             (map-indexed
              (fn [k p]
                (let [[x y vx vy dir color] p
                      x (mod (update-pos x (* speed vx)) (q/width))
                      y (mod (update-pos y (* speed vy)) (q/height))]
                  (aset p 0 x)
                  (aset p 1 y)
                  (aset p 2 (update-vel vx (Math/cos dir)))
                  (aset p 3 (update-vel vy (Math/sin dir)))
                  (aset p 4 (update-acc x y k angle noise uniqueness))
                  p))
              particles))))

(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update."
  [{:keys [time clear bg size particles palette opacity] :as state}]
  (when (pos? clear)
    (let [zone 200]
      (apply q/fill (:background palette))
      (q/rect (- (q/width) clear (/ zone 2))
              0
              zone
              (q/height))))

  (when bg
    (apply q/background [0 0 0]))

  (doseq [p particles]
    (let [[x y vx vy dir color] p
          color (conj color (if bg 255 opacity))
          size (+ (/ size 2) (clamp-sin (/ time 1000)))]
      (apply q/fill color)
      (q/ellipse x y size size))))


(defn initial-state []
  {:bg false 
   :time 0
   :noise (+ 0.002 (rand 0.03))
   :angle (rand (* 2 Math/PI))
   :size 3
   :uniqueness 1
   :speed 2
   ;; :opacity 255
   :opacity 5
   :palette palette})


(defn sketch-setup
  "Returns the initial state to use for the update-render loop."
  []
  (q/no-stroke)
  (apply q/background (:background palette))
  (merge
   {:particles (particles 500)}
   (initial-state)))


(defn mouse
  [{:keys [time angle particles] :as state}]
  (let [x (q/mouse-x)
        y (q/mouse-y)]
    (-> state
        (assoc :angle (* 2 Math/PI (/ x (q/width))))
        (assoc :noise (* 0.08 (/ y (q/height)))))))


(defn key-pressed
  [state e]
  (println "key" (:key e))
  (let [new-state
        (condp = (:key e)
          :C (assoc state :clear (q/width))
          :R (merge state (initial-state))
          :c (let [shuffle (create-shuffle)]
               (update-in state [:particles]
                         (partial map (fn [p]
                                        (aset p 5 (first (shuffle [(aget p 5)])))
                                        p))))
          (keyword "n") (-> state
                            (update :bg not)
                            (assoc :clear (q/width)))
          (keyword "m") (update state :uniqueness (partial + -0.01))
          (keyword "M") (update state :uniqueness (partial + 0.01))
          (keyword "_") (update state :speed dec)
          (keyword "-") (update state :speed inc)
          (keyword ";") (update state :opacity dec)
          (keyword ",") (update state :opacity inc)
          (keyword ":") (update state :size dec)
          (keyword ".") (update state :size inc)
          (do
            (save-image state e)))]
    (debug (select-keys
            new-state
            [:angle :noise :opacity :size :speed :uniqueness])
           "state")
    new-state))


(defn create
  "Creates a sketch that draws flow lines following a perlin noise field."
  [canvas]
  (js/setTimeout
   (fn []
     (let [[x y] (size)
           size [x
                 ;; y
                 (.-scrollHeight (.querySelector (.-body js/document) "#content"))
                 ]]
       (q/defsketch perlin-flow
         :host canvas
         :size size
         :renderer :p2d
         :settings (fn []
                     (q/pixel-density 1)
                     (q/random-seed 666)
                     (q/noise-seed 666))
         :setup sketch-setup
         :update sketch-update
         :draw sketch-draw
         :middleware [m/fun-mode]
         :mouse-pressed mouse
         ;; :mouse-moved mouse
         :key-pressed key-pressed)))
   0))
