(ns media-mogul.frontend.main-menu
 [ :use [ media-mogul.frontend [ callbacks :only [ updater renderer ] ] ] ]
 [ :require [ media-mogul.library :as library ] ])

(import
 '(org.newdawn.slick Color)
 '(org.newdawn.slick.util Log))

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

(defn- render-series [ name idx graphics container ]
 (let [ text-height (.getHeight (.getFont graphics) "AbjQ") ]
  (.drawString graphics
   name
   30
   (+ 30 (* idx (+ 10 text-height))))))

(defn- list-series [ graphics container ]
 (dotimes
  [ n (count (library/series-names)) ]
  (render-series (nth (library/series-names) n) n graphics container)))

(defmethod renderer :main-menu
 [ state container graphics ]
 (list-series graphics container))
