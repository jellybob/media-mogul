(ns media-mogul.frontend.main-menu)

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

(defn render [ container graphics ]
  (draw-message graphics container))
