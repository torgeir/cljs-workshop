(ns lib.polygram.timers)


(defn immediate
  "Call function on next tick."
  [fn]
  (.setTimeout js/window fn 0))