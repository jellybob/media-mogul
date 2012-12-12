(ns media-mogul.frontend.main-menu
  ( :require [ media-mogul.library :as library ] ))

(import
  '(org.newdawn.slick Color))

(def text-color (atom (new Color 255 0 0)))
(def message (atom "Hello world!"))

(defn update [ container delta ]
  (.setTitle container "Hello world"))

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

(defn render [ container graphics ]
  (list-series graphics container))
