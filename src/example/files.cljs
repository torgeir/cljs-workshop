(ns example.files)


(defn download-jpeg [canvas filename]
  (let [url  (.toDataURL canvas "image/jpeg")
        link (.createElement js/document "a")]
    (.appendChild (.-body js/document) link)
    (set! (.-href link) url)
    (set! (.-download link) (str filename ".jpeg"))
    (.click link)
    (.removeChild (.-body js/document) link)))
