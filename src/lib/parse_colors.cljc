(ns lib.parse-colors)

(comment

  ;; put colors as a json array in the file target/colors.js

  ;; run this

  (->> "target/colors.js"
    java.io.File.
    slurp
    clojure.data.json/read-json
    (spit "target/palettes.edn"))

  ;; copy the palettes from target/palettes.edn to sketches/palette.cljs

  )


