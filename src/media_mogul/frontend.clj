(ns media-mogul.frontend
 (:gen-class)
 (:use quil.core)
 (:require [ media-mogul.frontend.view :as view ]))

(defsketch frontend
  :title "Media Mogul"
  :setup view/setup
  :draw view/draw
  :size [(screen-width) (screen-height)]
  :renderer :opengl
  :decor false)
