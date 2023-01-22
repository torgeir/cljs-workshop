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
  {:name       "purple haze"
   :background [0 0 0]
   :colors     [[32 0 40]
                [82 15 125]
                [99 53 126]
                [102 10 150]
                [132 26 200]
                [165 32 250]
                [196 106 251]]})

(def rand-shift
  (let [times (rand-int 3)
        shifted (partial iterate (partial map shift))]
    #(nth (shifted %) times)))


(defn particle [index]
  {:id     index ;; idea: set id equal to color-index?
   :x      (rand (q/width))
   :y      (rand (q/height))
   :vx     0
   :vy     0
   :dir    0
   :time   (rand-int 100000)
   :color  (rand-nth (rand-shift (:colors palette)))})


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

(defn update-acc [x y z t angle noise uniqueness]
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
            [:angle :noise :opacity :size :speed :uniqueness])
           "state"))
  (-> state 
      (update-in [:time] inc)
      (update-in [:clear] dec-or-zero)
      (update-in [:uniqueness] identity)
      (assoc :particles
             (map (fn [p]
                    (let [x (mod (update-pos (:x p) (* speed (:vx p))) (q/width))
                          y (mod (update-pos (:y p) (* speed (:vy p))) (q/height))
                          time (inc (:time p))]
                      (assoc p
                             :time time
                             :x    x
                             :y    y
                             :vx   (update-vel (:vx p) (Math/cos (:dir p)))
                             :vy   (update-vel (:vy p) (Math/sin (:dir p)))
                             :dir  (update-acc x y (:id p) time angle noise uniqueness))))
                  particles))))


(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update."
  [{:keys [time clear size particles palette opacity] :as state}]
  (when (pos? clear)
    (let [zone 200]
      (q/fill 0)
      (q/rect (- (q/width) clear (/ zone 2))
              0
              zone
              (q/height))))

  (doseq [p particles]
    (let [p (assoc p
                   :color (conj (:color p) opacity)
                   :size (+ size (clamp-sin (/ time 1000))))]
      (apply q/fill (:color p))
      (q/ellipse
       (:x p)
       (:y p)
       (:size p)
       (:size p)))))


(def initial-state
  {:time 0
   :noise (+ 0.002 (rand 0.03))
   :angle (rand (* 2 Math/PI))
   :size 2
   :uniqueness 1
   :speed 1
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
   ;; {:particles (particles 1)}
   initial-state))


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
          :S (-> state
                 (update-in [:palette :colors] #(map shuffle %)))
          :c (assoc state :clear (q/width))
          :R (merge state initial-state)
          :C (update-in state [:particles] (partial map (fn [particle] (update-in particle [:color] shift))))
          (keyword "m") (update state :uniqueness (partial + -0.01))
          (keyword "M") (update state :uniqueness (partial + 0.01))
          (keyword "-") (update state :speed dec)
          (keyword "_") (update state :speed inc)
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
  (println (size))
  (q/defsketch perlin-flow
    :host canvas
    :size (size)
    :renderer :p2d
    :settings (fn []
                (q/pixel-density 2)
                (q/random-seed 666)
                (q/noise-seed 666))
    :setup sketch-setup
    :update sketch-update
    :draw sketch-draw
    :middleware [m/fun-mode]
    :mouse-pressed mouse
    ;; :mouse-moved mouse
    :key-pressed key-pressed))
