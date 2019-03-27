(ns example.tutorial)


;; "everything is a list"
(+ 1 2)
(* 3 (+ 1 2))


;; attempts to call the first element in a list as a function
(+ 1 2)
;; hence, this does not work
;;(1 2 3)


;; nil represents nothing
nil


;; seq
'(1 2 3)
(list 1 2 3)


;; clojure has literal data types as well
;; vector
[1 2 3]


;; commas are optional, nobody uses them
[1, 2, 3]


;; keywords
:yo


;; map
{}
{:one "hello" :two "more"}


;; keywords can look themselves up in a map
(:one {:one "hello" :two "more"})


;; set
#{1 2 3}
#{1 2 (inc 1) 3}


;; sets can check if they contain a value
(#{1 2 3} 2)
(#{1 2 3} 4)


;; anonymous function
(fn [a b]
  (+ a b))


;; can ble placed first in a list to be evaluated
((fn [a b]
   (+ a b)) 1 2)


;; def defines a
(def add
  "adds two numbers"
  (fn [a b]
    (+ a b)))
(add 1 2)


;; defn defines a function (shorthand for the above)
(defn also-add
  "also adds two numbers"
  [a b]
  (+ a b))
(also-add 1 2)


;; let defines a block with variables
(let [n 1
      m 2]
  (add n m))


;; rich std lib
(inc 1)
(dec 1)
(first [1 2 3 4])
(second [1 2 3 4])
(rest (range 0 10))
(assoc {} :nokkel "verdi")
(get-in {:one {:two 2}} [:one :two])
(get-in {:one {:two 2}} [:one :two :three] 666)
;; ++ https://cljs.info/cheatsheet/


;; immutable by default
(def m {:a 1})
(update m :a inc)
m


;; everything returns something
(+ 2 (if true
       42
       666))


;; when is like if, without the else
(when true
  42)


;; returns nil when it does not match
(when false
  666)


;; clj, embraces java - all of java is available in clj
;; cljs, embraces js - all of js is available in cljs
(new js/Date)
(.getTime (new js/Date))


;; clojure is built with the google closure compiler
;; meaning it has access to the closure-library
;; https://google.github.io/closure-library/api/goog.html
(defonce body (dom/getElement "app"))


;; map, filter reduce
(map inc (range 3))
(filter pos? (range -10 10))
(reduce + (range 4))


;; data is code, code is data!?
'(1 2 3)
'(if true 42 666)
(first '(if true 42 666))


;; next level meta programming abilities
(let [code '(if true 42 666)]
  (concat (take 2 code)
          (reverse (drop 2 code))))