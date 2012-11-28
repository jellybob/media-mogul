(ns media-mogul.frontend.example)

(import '(org.newdawn.slick Color))

(defn update [ container delta ]
 (.setTitle container "Now in the example renderer"))

(defn render [ container graphics ])
