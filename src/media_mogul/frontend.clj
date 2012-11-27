(ns media-mogul.frontend
  (:require [ media-mogul.frontend.main-menu ]))

(import
  '(org.newdawn.slick AppGameContainer)
  '(org.newdawn.slick.state StateBasedGame))

(def graphics-options {
  :width 1024
  :height 768
  :fullscreen false
  })

(def state-handler
  (proxy [ StateBasedGame ] [ "Frontend" ]
   (initStatesList [ container ]
     (.addState this media-mogul.frontend.main-menu/state-proxy))))

(defn start []
  (let [ gui (new AppGameContainer state-handler) ]
   (.setDisplayMode gui
      (:width graphics-options)
      (:height graphics-options)
      (:fullscreen graphics-options))
   (.setUpdateOnlyWhenVisible gui false)
   (.setAlwaysRender gui true)
   (.setTargetFrameRate gui 60)
   (.start gui)
   (gui)))
