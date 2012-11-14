(ns media-mogul.core
  [ :require [ clojure.java.io :as io ] ]
  [ :require [ clojure.string :as string ] ])

(def media-path
 "The root to look in for media files"
 "/Volumes/shared/TV/")

(def supported-files
 "File types to include in the media list"
  #{"mp4" "mkv" "avi"})

(def blacklist-patterns
  "Files matching these regexps will be excluded from the media list"
  [
    #".*/\..*" ; Hidden files and directories
    #".*\.sample.*" ; Samples
  ])

(def metadata-matchers
  "Regexps that will be tried to extract a series and episode number from the filename"
  [
    #".*s(\d+)e(\d+).*" ; Series Name - s01e02 - Episode Name
    #".*s(\d+)e(\d+).*" ; Series Name - s01_e02 - Episode Name
    #".*\.s(\d+)e(\d+).*?" ; Series.Name-s01e02-Episode.Name
    #".*\[(\d+)x(\d+)\].*?" ; Series Name [1x2] Episode Name
    #".*(\d+)x(\d+).*?" ; Series.Name.1x01.Episode.Name
  ])

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

(defn collect-matches [ patterns s ]
  (map #(re-matches % s) patterns))

(defn blacklisted-path? [ path ]
  (some #(not (nil? %)) (collect-matches blacklist-patterns path)))

(defn find-media []
  "Find any files that are both valid file types and not blacklisted"
  (filter #(and (supported-file-type? %) (not (blacklisted-path? (% :path)))) (files-in-path media-path)))

(defn metadata [ file ]
  "Extract series and episode metadata from a file's path"
  (let [
      matches (collect-matches metadata-matchers (string/lower-case (file :filename)))
      valid-matches (filter #(not (nil? %)) matches)
      [ _ season episode ] (first valid-matches)
    ]

    (assoc file
      :series (first (string/split (sanitized-path (file :path)) #"\/"))
      :season season
      :episode episode
    )))
