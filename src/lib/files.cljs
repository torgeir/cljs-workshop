(ns lib.files)


(defn download-jpeg
  "Convert canvas to blob, create a link to its object url and click it to trigger a jpeg file download.
  Like described in https://stackoverflow.com/questions/19327749/javascript-blob-filename-without-link."
  [canvas filename]
  (.toBlob canvas
           (fn [blob]
             (let [link (.createElement js/document "a")
                   url  (.createObjectURL js/URL blob)]
               (.appendChild (.-body js/document) link)
               (set! (.-href link) url)
               (set! (.-download link) (str filename ".jpeg"))
               (.click link)
               (js/setTimeout
                 #(do
                    (.removeChild (.-body js/document) link)
                    (.revokeObjectURL js/URL url)))))
           "image/jpeg"))