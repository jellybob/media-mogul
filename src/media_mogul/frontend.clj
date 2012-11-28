(ns media-mogul.frontend
  (:require [ media-mogul.frontend.main-menu ]))

(import '(org.newdawn.slick AppGameContainer BasicGame))

(def state
  (ref { :view-ns 'media-mogul.frontend.main-menu
         :show-fps false }))

(def graphics-options {
  :width 1024
  :height 768
  :fullscreen false
  })

(defn set-view [ view-ns ]
  (dosync
    (alter state conj { :view-ns view-ns })))

(defn callback [ name & args ]
  (let [ callback-fn @(name (ns-publics (the-ns (:view-ns @state)))) ]
    (eval (apply callback-fn args))))

(def application-proxy
  (proxy [ BasicGame ] [ "Media Mogul" ]
    (init [ container ])
    (render [ container graphics ]
      (callback 'render container graphics))
    (update [ container delta ]
      (.setShowFPS container (:show-fps @state))
      (callback 'update container delta))))

(defn start []
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
