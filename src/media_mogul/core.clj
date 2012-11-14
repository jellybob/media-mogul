(ns media-mogul.core
  [ :require [ clojure.java.io :as io ] ]
  [ :require [ clojure.string :as string ] ])

(def supported-files
  #{"mp4" "mkv" "avi"})

(def blacklist-patterns
  [
    #".*/\..*" ; Hidden files and directories
    #".*\.sample.*" ; Samples
  ])

(def media-path "/Volumes/shared/TV/")

(defn file-extension [ path ]
  (last (string/split path #"\.")))

(defn file-details [ file ]
 (let [ path (.getPath file) name (.getName file) extension (file-extension name) ]
  {
   :path path
   :filename (string/replace name (str "." extension) "")
   :extension extension
  }))

(defn sanitized-path [ path ]
  (string/replace-first path media-path "")
  )

(defn files-in-path [ base_path ]
  (map #(file-details %) (file-seq (io/file base_path))))

(defn supported-file-type? [ file ]
  (contains? supported-files (file :extension)))

(defn collect-matches? [ patterns s ]
  (map #(re-matches % s) patterns))

(defn ignored-path? [ path ]
  (some #(not (nil? %)) (collect-matches? blacklist-patterns path)))

(defn find-media []
  (filter #(and (supported-file-type? %) (not (ignored-path? (% :path)))) (files-in-path media-path)))

(def metadata-matchers [
  #".*s(\d+)e(\d+).*" ; Series Name - s01e02 - Episode Name
  #".*s(\d+)e(\d+).*" ; Series Name - s01_e02 - Episode Name
  #".*\.s(\d+)e(\d+).*?" ; Series.Name-s01e02-Episode.Name
  #".*\[(\d+)x(\d+)\].*?" ; Series Name [1x2] Episode Name
  #".*(\d+)x(\d+).*?" ; Series.Name.1x01.Episode.Name
  ])

(defn metadata [ file ]
  (let [
      matches (map #(re-matches % (string/lower-case (file :filename))) metadata-matchers)
      valid-matches (filter #(not (nil? %)) matches)
      [ _ season episode ] (first valid-matches)
    ]

    (assoc file
      :series (first (string/split (sanitized-path (file :path)) #"\/"))
      :season season
      :episode episode
    )))
