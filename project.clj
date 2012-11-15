(defproject media-mogul "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-time "0.4.4"]
                 [org.lwjgl.lwjgl/lwjgl "2.8.4" ]
                 [org.clojars.jyaan/slick "247.1"]]
  :jvm-opts ["-Djava.library.path=./native"]
  :main media-mogul.core)
