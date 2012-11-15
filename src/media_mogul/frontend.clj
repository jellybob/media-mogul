(ns media-mogul.frontend
  (:use [ clj-time.format :only (formatters unparse)]
        [ clj-time.local :only (local-now) ]))

(import '(org.newdawn.slick BasicGame GameContainer Graphics SlickException AppGameContainer))

(defn clock-time []
  (unparse (formatters :hour-minute-second) (local-now)))

(def main-menu
  (proxy [ BasicGame ] [ "Frontend" ]
   (init [ container ])

   (update [ container delta ])

   (render [ container graphics ]
    (.drawString graphics (clock-time) 30 (- 1024 150)))))

(defn start [ options ]
  (let [ menu (new AppGameContainer main-menu) ]
   (.setDisplayMode menu
    (get options :width 1024)
    (get options :height 768)
    (get options :fullscreen false))
   (.start menu)))
