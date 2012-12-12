(ns media-mogul.core)

(def config
  "Application config as loaded from the config file."
  (ref (load-file "config.clj")))

(defn reload-configuration []
  (dosync
    (alter config conj (load-file "config.clj"))))
