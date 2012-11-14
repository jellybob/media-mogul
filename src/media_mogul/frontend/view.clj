(ns media-mogul.frontend.view
  (:use quil.core))

(defn setup []
  (smooth)
  (frame-rate 60))

(defn draw-clock []
  (text-size 20)
  (color 255)
  (let [ x (- (width) 100) y 30 ]
    (text (str (quil.core/hour) ":" (quil.core/minute) ":" (quil.core/seconds)) x y)))

(defn draw []
  (background 000)
  (draw-clock))
