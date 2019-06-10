(ns example.palette
  (:require [goog.color :as color]))


(defn hex-to-rgb [hex]
  (js->clj (color/hexToRgb hex)))


(def palettes
  "Color palettes from https://kgolid.github.io/chromotome-site/"
  [{:name       "cc239"
    :colors     ["#e3dd34" "#78496b" "#f0527f" "#a7e0e2"]
    :background "#e0eff0"}
   {:name       "cc234"
    :colors     ["#ffce49" "#ede8dc" "#ff5736" "#ff99b4"]
    :background "#f7f4ed"}
   {:name       "cc232"
    :colors     ["#5c5f46" "#ff7044" "#ffce39" "#66aeaa"]
    :background "#e9ecde"}
   {:name       "cc238"
    :colors     ["#553c60" "#ffb0a0" "#ff6749" "#fbe090"]
    :background "#f5e9de"}
   {:name       "cc242"
    :colors     ["#bbd444" "#fcd744" "#fa7b53" "#423c6f"]
    :background "#faf4e4"}
   {:name       "cc245"
    :colors     ["#0d4a4e" "#ff947b" "#ead3a2" "#5284ab"]
    :background "#f6f4ed"}
   {:name       "cc273"
    :colors     ["#363d4a" "#7b8a56" "#ff9369" "#f4c172"]
    :background "#f0efe2"}


   {:name       "ducci_jb"
    :colors     ["#395e54" "#e77b4d" "#050006" "#e55486"]
    :stroke     "#050006"
    :background "#efe0bc"}
   {:name       "ducci_a"
    :colors     ["#809498" "#d3990e" "#000000" "#ecddc5"]
    :stroke     "#ecddc5"
    :background "#863f52"}
   {:name       "ducci_b"
    :colors     ["#ecddc5" "#79b27b" "#000000" "#ac6548"]
    :stroke     "#ac6548"
    :background "#d5c08e"}
   {:name       "ducci_d"
    :colors     ["#f3cb4d" "#f2f5e3" "#20191b" "#67875c"]
    :stroke     "#67875c"
    :background "#433d5f"}
   {:name       "ducci_e"
    :colors     ["#c37c2b" "#f6ecce" "#000000" "#386a7a"]
    :stroke     "#386a7a"
    :background "#e3cd98"}
   {:name       "ducci_f"
    :colors     ["#596f7e" "#eae6c7" "#463c21" "#f4cb4c"]
    :stroke     "#f4cb4c"
    :background "#e67300"}
   {:name       "ducci_g"
    :colors     ["#c75669" "#000000" "#11706a"]
    :stroke     "#11706a"
    :background "#ecddc5"}
   {:name       "ducci_h"
    :colors     ["#6b5c6e" "#4a2839" "#d9574a"]
    :stroke     "#d9574a"
    :background "#ffc34b"}
   {:name       "ducci_i"
    :colors     ["#e9dcad" "#143331" "#ffc000"]
    :stroke     "#ffc000"
    :background "#a74c02"}
   {:name       "ducci_j"
    :colors     ["#c47c2b" "#5f5726" "#000000" "#7e8a84"]
    :stroke     "#7e8a84"
    :background "#ecddc5"}
   {:name       "ducci_o"
    :colors     ["#c15e1f" "#e4a13a" "#000000" "#4d545a"]
    :stroke     "#4d545a"
    :background "#dfc79b"}
   {:name       "ducci_q"
    :colors     ["#4bae8c" "#d0c1a0" "#2d3538"]
    :stroke     "#2d3538"
    :background "#d06440"}
   {:name       "ducci_u"
    :colors     ["#f6d700" "#f2d692" "#000000" "#5d3552"]
    :stroke     "#5d3552"
    :background "#ff7426"}
   {:name       "ducci_v"
    :colors     ["#c65f75" "#d3990e" "#000000" "#597e7a"]
    :stroke     "#597e7a"
    :background "#f6eccb"}
   {:name       "ducci_x"
    :colors     ["#dd614a" "#f5cedb" "#1a1e4f"]
    :stroke     "#1a1e4f"
    :background "#fbb900"}


   {:name       "iiso_zeitung"
    :colors     ["#ee8067" "#f3df76" "#00a9c0" "#f7ab76"]
    :stroke     "#111a17"
    :background "#f5efcb"}
   {:name       "iiso_curcuit"
    :colors     ["#f0865c" "#f2b07b" "#6bc4d2" "#1a3643"]
    :stroke     "#0f1417"
    :background "#f0f0e8"}
   {:name       "iiso_airlines"
    :colors     ["#fe765a" "#ffb468" "#4b588f" "#faf1e0"]
    :stroke     "#1c1616"
    :background "#fae5c8"}
   {:name       "iiso_daily"
    :colors     ["#e76c4a" "#f0d967" "#7f8cb6" "#1daeb1" "#ef9640"]
    :stroke     "#000100"
    :background "#e2ded2"}


   {:name       "jud_playground"
    :colors     ["#f04924" "#fcce09" "#408ac9"]
    :stroke     "#2e2925"
    :background "#ffffff"}
   {:name       "jud_horizon"
    :colors     ["#f8c3df" "#f2e420" "#28b3d0" "#648731" "#ef6a7d"]
    :stroke     "#030305"
    :background "#f2f0e1"}
   {:name       "jud_mural"
    :colors     ["#ca3122" "#e5af16" "#4a93a2" "#0e7e39" "#e2b9bd"]
    :stroke     "#1c1616"
    :background "#e3ded8"}
   {:name       "jud_cabinet"
    :colors     ["#f0afb7" "#f6bc12" "#1477bb" "#41bb9b"]
    :stroke     "#020508"
    :background "#e3ded8"}


   {:name       "rag-mysore"
    :colors     ["#ec6c26" "#613a53" "#e8ac52" "#639aa0"]
    :background "#d5cda1"}
   {:name       "rag-gol"
    :colors     ["#d3693e" "#803528" "#f1b156" "#90a798"]
    :background "#f0e0a4"}
   {:name       "rag-belur"
    :colors     ["#f46e26" "#68485f" "#3d273a" "#535d55"]
    :background "#dcd4a6"}
   {:name       "rag-bangalore"
    :colors     ["#ea720e" "#ca5130" "#e9c25a" "#52534f"]
    :background "#f9ecd3"}
   {:name       "rag-taj"
    :colors     ["#ce565e" "#8e1752" "#f8a100" "#3ac1a6"]
    :background "#efdea2"}
   {:name       "rag-virupagksha"
    :colors     ["#f5736a" "#925951" "#feba4c" "#9d9b9d"]
    :background "#eedfa2"}


   {:name       "rohlfs_1R"
    :colors     ["#004996" "#567bae" "#ff4c48" "#ffbcb3"]
    :stroke     "#004996"
    :background "#fff8e7"}
   {:name       "rohlfs_1Y"
    :colors     ["#004996" "#567bae" "#ffc000" "#ffdca4"]
    :stroke     "#004996"
    :background "#fff8e7"}
   {:name       "rohlfs_1G"
    :colors     ["#004996" "#567bae" "#60bf3c" "#d2deb1"]
    :stroke     "#004996"
    :background "#fff8e7"}
   {:name       "rohlfs_2"
    :colors     ["#4d3d9a" "#f76975" "#ffffff" "#eff0dd"]
    :stroke     "#211029"
    :background "#58bdbc"}
   {:name       "rohlfs_3"
    :colors     ["#abdfdf" "#fde500" "#58bdbc" "#eff0dd"]
    :stroke     "#211029"
    :background "#f76975"}
   {:name       "rohlfs_4"
    :colors     ["#fde500" "#2f2043" "#f76975" "#eff0dd"]
    :stroke     "#211029"
    :background "#fbbeca"}])


(defn palettes-lists
  [{:name   "retro"
    :colors ["#69766f"
             "#9ed6cb"
             "#f7e5cc"
             "#9d8f7f"
             "#936454"
             "#bf5c32"
             "#efad57"]}
   {:name   "retro-washedout"
    :colors ["#878a87"
             "#cbdbc8"
             "#e8e0d4"
             "#b29e91"
             "#9f736c"
             "#b76254"
             "#dfa372"]}
   {:name   "roygbiv-warm"
    :colors ["#705f84"
             "#687d99"
             "#6c843e"
             "#fc9a1a"
             "#dc383a"
             "#aa3a33"
             "#9c4257"]}
   {:name   "roygbiv-toned"
    :colors ["#817c77"
             "#396c68"
             "#89e3b7"
             "#f59647"
             "#d63644"
             "#893f49"
             "#4d3240"]}
   {:name   "present-correct"
    :colors ["#fd3741"
             "#fe4f11"
             "#ff6800"
             "#ffa61a"
             "#ffc219"
             "#ffd114"
             "#fcd82e"
             "#f4d730"
             "#ced562"
             "#8ac38f"
             "#79b7a0"
             "#72b5b1"
             "#5b9bae"
             "#6ba1b7"
             "#49619d"
             "#604791"
             "#721e7f"
             "#9b2b77"
             "#ab2562"
             "#ca2847"]}])


(defn palette [name]
  (->> palettes
    (filter #(= name (:name %)))
    (first)))


(def nice-palette
  (->
      ;; (nth palettes 0)
      ;; (palette "rohlfs_4")
      (palette "ducci_h")
    (update :colors #(map hex-to-rgb %))))
