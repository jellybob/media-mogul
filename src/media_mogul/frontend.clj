(ns media-mogul.frontend)
(import '(org.newdawn.slick BasicGame GameContainer Graphics SlickException AppGameContainer))

(def main-menu
  (proxy [BasicGame] ["Frontend"]
   (init [container])
   (update [container delta])
   (render [container graphics]
    (.drawString graphics "Hello, world!" 0 100))))

(defn start []
  (.start (new AppGameContainer main-menu)))
