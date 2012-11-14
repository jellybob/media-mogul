(ns media-mogul.library
  [ :require [ media-mogul.core :as config ] ]
  [ :require [ clojure.java.io :as io ] ]
  [ :require [ clojure.string :as string ] ])

(defn -file-extension [ path ]
  (last (string/split path #"\.")))

(defn -file-details [ file ]
 (let [ path (.getPath file) name (.getName file) extension (-file-extension name) ]
  {
   :path path
   :filename (string/replace name (str "." extension) "")
   :extension extension
  }))

(defn -sanitized-path [ path ]
  (string/replace-first path config/media-path ""))

(defn -files-in-path [ base_path ]
  (map #(-file-details %)
   (file-seq (io/file base_path))))

(defn -supported-file-type? [ file ]
  (contains? config/supported-file-types (file :extension)))

(defn -collect-matches [ patterns s ]
  (map #(re-matches % s) patterns))

(defn -blacklisted-path? [ path ]
  (some #(not (nil? %))
   (-collect-matches config/blacklisted-paths path)))

(defn -find-media []
  "Find any files that are both valid file types and not blacklisted"
  (filter #(and
            (-supported-file-type? %)
            (not (-blacklisted-path? (% :path))))
   (-files-in-path config/media-path)))

(defn -metadata [ file ]
  "Extract series and episode metadata from a file's path"
  (let [
      path (-sanitized-path (file :path))
      matches (-collect-matches config/metadata-matchers (string/lower-case (file :filename)))
      valid-matches (filter #(not (nil? %)) matches)
      [ _ season episode ] (first valid-matches)
    ]

    (assoc file
      :series (first (string/split path #"\/"))
      :season season
      :episode episode
    )))

(defn metadata []
  (map #(-metadata %) (-find-media)))
