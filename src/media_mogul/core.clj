(ns media-mogul.core
  [ :require [ clojure.java.io :as io ] ]
  [ :require [ clojure.string :as string :only [ :split ]  ] ])

(def supported-files
  #{"mp4" "mkv" "avi"})

(def media-path "/Volumes/shared/TV/")

(defn files-in-path [ base_path ]
  (map #(file-details %) (file-seq (io/file base_path))))

(defn file-extension [ path ]
  (last (string/split path #"\.")))

(defn supported-file-type? [ file ]
  (contains? supported-files (file :extension)))

(defn find-media []
  (filter supported-file-type? (files-in-path media-path)))


(defn file-details [ file ]
 (let [ path (.getPath file) name (.getName file) extension (file-extension name) ]
  {
   :path path
   :filename (string/replace name (str "." extension) "")
   :extension extension
  }))

(def metadata-matchers [
  #"(.*) - s(\d+)e(\d+) - (.*)" ; Series Name - s01e02 - Episode Name
  #"(.*) - s(\d+)e(\d+) - (.*)" ; Series Name - s01_e02 - Episode Name
  #"(.*)\.s(\d+)e(\d+)\.(.*)" ; Series.Name-s01e02-Episode.Name
  #"(.*) \[(\d+)x(\d+)\] (.*)" ; Series Name [1x2] Episode Name
  #"(.*)(\d+)x(\d+)(.*)" ; Series.Name.1x01.Episode.Name
  ])

(defn metadata [ filename ]
  (def matches (map #(re-matches % (string/lower-case filename)) metadata-matchers))
  (def actual-match (first (filter #(not (nil? %)) matches)))

  (let [ [ _ series season episode title ] actual-match ]
    {
      :series series
      :season season
      :episode episode
      :title title
    }))
