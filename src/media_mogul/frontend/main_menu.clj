(ns media-mogul.frontend.main-menu)

(import
  '(org.newdawn.slick GameContainer Graphics SlickException Color)
  '(org.newdawn.slick.state BasicGameState StateBasedGame))

(def text-color (atom (new Color 255 0 0)))
(def message (atom "Hello world!"))
(def show-fps (atom false))

(defn -init [ container game ])

(defn -update [ container game delta ]
  (.setShowFPS container @show-fps)
  (.setTitle container "Hello world"))

(defn -draw-message [ graphics container ]
  (let [ current-message @message
         text-width (.getWidth (.getFont graphics) current-message) ]
    (.setColor graphics (new Color @text-color))
    (.drawString graphics @message (- (.getWidth container) text-width 30) 30)))

(defn -render [ container game graphics ]
    (-draw-message graphics container))

(def state-proxy
  (proxy [ BasicGameState ] []
    (getID [] 1)

    (init [ container game ]
      (-init container game))

    (update [ container game delta ]
      (-update container game delta))

    (render [ container game graphics ]
      (-render container game graphics))))
