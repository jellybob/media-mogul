(ns media-mogul.frontend
  (:require [ media-mogul.frontend.main-menu ])
  (:use [ media-mogul [ core :only [ config ] ] ])
  (:gen-class))

(import '(org.newdawn.slick AppGameContainer BasicGame))

(declare start state callback application-proxy)

(defn -main [ & args ]
  (future (start)))

(defn start
 "Starts the GUI using the display options set in graphics-options.

 This method will start an infinite event loop, so it should probably be
 run in its own thread using (future (start))"
 []

 (let [ container (new AppGameContainer application-proxy) ]
   (doto container
     (.setDisplayMode
        (:width @state)
        (:height @state)
        (:fullscreen @state))
     (.setUpdateOnlyWhenVisible false)
     (.setAlwaysRender true)
     (.setTargetFrameRate 60)
     (.start))))

(defn view
  "Defines the namespace to look for update and render functions to display
  the current state of the GUI. See media-mogul.frontend.example for an idea
  of what that looks like"
  [ view-ns ]

  (dosync
    (alter state conj { :view-ns view-ns }))

  view-ns)

(defn display-mode
  "Updates the desired display mode, which will be put into effect on the
  next run of the update loop."
  [ width height fullscreen ]
  (dosync
    (alter config update-in [ :display ] conj {
      :width width
      :height height
      :fullscreen fullscreen }))

  { :width width :height height :fullscreen fullscreen })

(defn show-fps
  "Specifies whether the FPS counter should be displayed."
  [ fps ]
  (dosync
    (alter config update-in [ :display ] conj { :show-fps fps }))

  fps)

(def application-proxy
  (proxy [ BasicGame ] [ "Media Mogul" ]
    (init [ container ])
    (render [ container graphics ]
      (callback 'render container graphics))
    (update [ container delta ]
      (doto container
        (.setShowFPS (:show-fps @state))
        (.setDisplayMode (:width @state) (:height @state) (:fullscreen @state)))
      (callback 'update container delta))))

(add-watch config :config-changed (fn [ key config old-val new-val ]
                                    (dosync (alter state conj (:display new-val)))))

(def state
  (ref (conj {
         :view-ns 'media-mogul.frontend.main-menu
         :show-fps false
         :fullscreen false
         :width 1024
         :height 768 } (:display @config))))

(defn- callback [ name & args ]
  (let [ callback-fn @(name (ns-publics (the-ns (:view-ns @state)))) ]
    (eval (apply callback-fn args))))
