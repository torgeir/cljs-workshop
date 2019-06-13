(ns lib.polygram.log)


(defn log [& args]
  (apply (.-log js/console) args))

