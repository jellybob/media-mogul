(ns media-mogul.frontend.main-menu
  [ :use [ media-mogul.frontend [ callbacks :only [ updater renderer ] ] ] ]
  [ :require [ media-mogul.library :as library ] ])

(import
  '(org.newdawn.slick Color))

(def text-color (atom (new Color 255 0 0)))
(def message (atom "Hello world!"))

(defmethod updater :main-menu
  [ state container delta ]
  (.setTitle container "Main menu"))

(defn- draw-message [ graphics container ]
  (let [ current-message @message
         text-width (.getWidth (.getFont graphics) current-message) ]
    (.setColor graphics (new Color @text-color))
    (.drawString graphics @message (- (.getWidth container) text-width 30) 30)))

(defn- list-series [ graphics container ]
 (let [ names (library/series-names)
        indexes (range (count names))
        text-height (.getHeight (.getFont graphics) "AbjQ") ]
  (map #(.drawString graphics (nth names %) 30 (+ 30 (* % text-height))) indexes)))

(defmethod renderer :main-menu
  [ state container graphics ]
  (draw-message graphics container))
