(require '[nrepl.server :refer [start-server default-handler stop-server]]
         '[cider.piggieback :refer [wrap-cljs-repl]]
         '[figwheel.main.api :as fig])


;; file used for vscode+calva setup


;; run nrepl server that vscode can connect to
(defonce server
  (start-server :port 4000
                :bind "localhost"
                ;; wrap repl in piggieback repl to allow cljs evaluation on top of the clj repl
                :handler (default-handler #'wrap-cljs-repl)))


;; run the figwheel dev build in background mode
(fig/start {:mode :serve} "dev")


;; start a cljs repl
(fig/cljs-repl "dev")