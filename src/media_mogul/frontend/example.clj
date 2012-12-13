(ns media-mogul.frontend.example
  [ :use [ media-mogul.frontend [ callbacks :only [ updater renderer ] ] ] ])

(import '(org.newdawn.slick Color))

(defmethod updater :example
  [ state container delta ]
  (.setTitle container "Now in the example renderer"))

(defmethod renderer :example
  [ state container graphics ])
