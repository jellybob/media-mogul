(ns media-mogul.frontend.view
  (:use quil.core)
  (:require [ clojure.string :as string ])
  (:require [ media-mogul.library :as library ]))

(def selected 4)

(defn setup []
  (smooth)
  (frame-rate 5))

(defn clock-time []
  (string/join ":"
    (map #(format "%02d" %) [ (quil.core/hour) (quil.core/minute) (quil.core/seconds) ])))

(defn draw-clock []
  (text-size 20)
  (color 255)
  (let [ x (- (width) 100) y 30 ]
    (text (clock-time) x y)))

(defn draw-menu [ & items ]
  (map #(text % 200 200) items))

(defn draw-menu-item [ position item ]
  (println position selected)
  (if (= position selected)
    (color 255 0 0)
    (color 0 0 0))

  (text item 20 (* (+ position 1) 30)))

(defn draw []
  (background 000)

  (dotimes [ i (count library/series-names) ]
    (draw-menu-item i (nth library/series-names i)))

  (draw-clock))
