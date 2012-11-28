(ns media-mogul.frontend
  (:require [ media-mogul.frontend.main-menu ]))

(import '(org.newdawn.slick AppGameContainer BasicGame))

(declare state callback application-proxy set-view graphics-options)

(defn start
 "Starts the GUI using the display options set in graphics-options.

 This method will start an infinite event loop, so it should probably be
 run in its own thread using (future (start))"
 []

 (let [ container (new AppGameContainer application-proxy) ]
   (doto container
     (.setDisplayMode
        (:width graphics-options)
        (:height graphics-options)
        (:fullscreen graphics-options))
     (.setUpdateOnlyWhenVisible false)
     (.setAlwaysRender true)
     (.setTargetFrameRate 60)
     (.start))))

(defn set-view
  " Defines the namespace to look for update and render functions to display
  the current state of the GUI. See media-mogul.frontend.example for an idea
  of what that looks like"
  [ view-ns ]

  (dosync
    (alter state conj { :view-ns view-ns })))

(def graphics-options {
  :width 1024
  :height 768
  :fullscreen false
  })

(def ^{ :private true }
  application-proxy
  (proxy [ BasicGame ] [ "Media Mogul" ]
    (init [ container ])
    (render [ container graphics ]
      (callback 'render container graphics))
    (update [ container delta ]
      (.setShowFPS container (:show-fps @state))
      (callback 'update container delta))))

(def
  ^{ :private true }
  state
  (ref { :view-ns 'media-mogul.frontend.main-menu
         :show-fps false }))

(defn
  ^{ :private true }
  callback [ name & args ]
  (let [ callback-fn @(name (ns-publics (the-ns (:view-ns @state)))) ]
    (eval (apply callback-fn args))))
