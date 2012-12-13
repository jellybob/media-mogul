(ns media-mogul.frontend.callbacks)

(defmulti renderer (fn [ state container graphics ] (:view state) ))
(defmethod renderer :default
  [ state container graphics ]
  (doto graphics
    (.drawString "Using default renderer", 30, 30)))

(defmulti updater (fn [ state container delta ] (:view state) ))
(defmethod updater :default
  [ state container delta ])
