(ns media-mogul.library
  ( :require [ media-mogul.core :as core ] )
  ( :require [ clojure.java.io :as io ] )
  ( :require [ clojure.string :as string ] ))

(defn -config []
  (:library @core/config))

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
  (string/replace-first path (:path (-config)) ""))

(defn -files-in-path [ base_path ]
  (map #(-file-details %)
   (file-seq (io/file base_path))))

(defn -supported-file-type? [ file ]
  (contains? (:supported-file-types (-config)) (file :extension)))

(defn -collect-matches [ patterns s ]
  (map #(re-matches % s) patterns))

(defn -blacklisted-path? [ path ]
  (some #(not (nil? %))
   (-collect-matches (:blacklisted-paths (-config)) path)))

(defn -find-media []
  "Find any files that are both valid file types and not blacklisted"
  (filter #(and
            (-supported-file-type? %)
            (not (-blacklisted-path? (% :path))))
   (-files-in-path (:path (-config)))))

(defn -metadata [ file ]
  "Extract series and episode metadata from a file's path"
  (let [
      path (-sanitized-path (file :path))
      matches (-collect-matches (:metadata-matchers (-config)) (string/lower-case (file :filename)))
      valid-matches (filter #(not (nil? %)) matches)
      [ _ season episode ] (first valid-matches)
    ]

    (assoc file
      :series (first (remove #(= % "") (string/split path #"\/")))
      :season season
      :episode episode
    )))

(defn load-metadata []
  (map #(-metadata %) (-find-media)))

(def metadata
  (ref (load-metadata)))

(defn series-names []
  (sort (distinct (map #(% :series) @metadata))))
