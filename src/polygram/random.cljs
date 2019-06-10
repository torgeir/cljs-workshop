(ns polygram.random)


(defn rand-no-repeat
  "Random non-repeating number from 0 to n."
  [n]
  (let [a (atom (range n))]
    (fn []
      (let [shuffled (shuffle @a)]
        (reset! a (rest shuffled))
        (first shuffled)))))

