(ns example.core
  (:require [cljs-test-display.core])
  (:require-macros [cljs.test :refer [deftest is testing run-tests]]))


(enable-console-print!)


(deftest it-adds-vectors
  (is (= [1 2 3 4]
         (map (partial apply +)
              (for [x [1 2]
                    y [1 2]]
                [x y])))))


(run-tests (cljs-test-display.core/init! "app")
           'example.core)