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
  (into {} (map (fn [[k v]] [k (f v)]) (seq m))))


(defn debug [v & [desc]]
  (do
    (println (or
              (and (map? v)
                   (or
                    (and desc (str desc ":")) "")
                   (map-vals (fn [v] (.toFixed v 3)) v)
                   )
              (and desc (str desc ": " v))
              v))
    v))


(def window js/window)


(def body (.-body js/document))


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


(defn particle [index]
  {:id      index ;; idea: set id equal to color-index?
   :x       (rand (q/width))
   :y       (rand (q/height))
   :vx      0
   :vy      0
   :time   (rand-int 100000)
   :adir    0
   :color-index (rand-int (count (:colors palette)))})


(defn particles [n]
  (map particle (range n)))


(defn update-pos [curr delta max]
  (mod (+ curr delta)
       max))


(defn update-vel [curr delta]
  (/ (+ curr delta) 2))


(defn update-acc [x y id t angle noise uniqueness]
  (*
   (+ (q/noise (* x noise) (* y noise) id)
      (* (q/noise (* x noise) (* y noise) id) uniqueness))
   angle))


(defn clamp-sin [v]
  (+ 0.5 (* 0.5 (Math/sin v))))

(defn dec-or-zero [v]
  (if (pos? v) (- v 100) 0))


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
      (update-in [:uniqueness] (fn [u]
                                 u))
      (assoc :particles
             (map (fn [p]
                    (assoc p
                           :time (inc (:time p))
                           :x    (update-pos (:x p)
                                             (* speed (:vx p))
                                             (q/width))
                           :y    (update-pos (:y p)
                                             (* speed (:vy p))
                                             (q/height))
                           :vx   (update-vel (:vx p) (Math/cos (:adir p)))
                           :vy   (update-vel (:vx p) (Math/sin (:adir p)))
                           :adir (update-acc
                                  (:x p) (:y p) (:id p) (:time p)
                                  angle
                                  noise
                                  uniqueness)))
                  particles))))


(defn tweak [p t palette opacity size]
  (assoc p
         :color (conj (nth (:colors palette)
                           (:color-index p))
                      (* opacity
                         ;; (* 0.2 (clamp-sin (/ t 100)))
                         ))
         :size (+ size
                  (clamp-sin (/ t 1000)))))

(defn sketch-draw
  "Draws the current state to the canvas. Called on each iteration after sketch-update."
  [{:keys [time clear size particles palette opacity] :as state}]
  (when (pos? clear)
    (let [padding 200]
      (q/fill 0)
      (q/rect (- (q/width) (/ (q/width) 2) clear (/ padding 2))
              (/ (q/height) -2)
              padding
              (q/height))))

  ;; (if (zero? (mod time 100))
  ;;   (debug (clamp-sin (/ time 300)) "t/300"))

  (doseq [p particles]
    (let [p (tweak p (:time p) palette opacity size)]
      (apply q/fill (:color p))
      (q/ellipse
       (+ (:x p) (/ (q/width)  -2))
       (+ (:y p) (/ (q/height) -2))
       (:size p)
       (:size p)))))


(def initial-state
  {:time 0
   :noise 0.002
   :angle (* 1.05 Math/PI)
   :size 1
   :uniqueness 1
   :speed 2
   :opacity 2
   :palette palette})


(defn sketch-setup
  "Returns the initial state to use for the update-render loop."
  []
  (q/no-stroke)
  (apply q/background (:background palette))
  (merge
   {:particles (particles 800)}
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
  (let [new-state
        (condp = (:key e)
          :S (-> state
                 (update-in [:palette :colors] #(map shuffle %)))
          :c (assoc state :clear (q/width))
          :R (merge state initial-state)
          :C (update-in state [:palette :colors] #(map shift %))
          (keyword "m") (update state :uniqueness (partial + -0.01))
          (keyword "M") (update state :uniqueness (partial + 0.01))
          (keyword "-") (update state :speed dec)
          (keyword "_") (update state :speed inc)
          (keyword ";") (update state :opacity dec)
          (keyword ",") (update state :opacity inc)
          (keyword ":") (update state :size dec)
          (keyword ".") (update state :size inc)
          (do
            (println "key" (:key e))
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
    :renderer :p3d
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
