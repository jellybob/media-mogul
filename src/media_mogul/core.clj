(ns media-mogul.core)

(def media-path
 "The root to look in for media files"
 "/Volumes/shared/TV/")

(def supported-file-types
 "File types to include in the media list"
  #{"mp4" "mkv" "avi"})

(def blacklisted-paths
  "Files matching these regexps will be excluded from the media list"
  [
    #".*/\..*" ; Hidden files and directories
    #".*\.sample.*" ; Samples
  ])

(def metadata-matchers
  "Regexps that will be tried to extract a series and episode number from the filename"
  [
    #".*s(\d+)e(\d+).*" ; Series Name - s01e02 - Episode Name
    #".*s(\d+)e(\d+).*" ; Series Name - s01_e02 - Episode Name
    #".*\.s(\d+)e(\d+).*?" ; Series.Name-s01e02-Episode.Name
    #".*\[(\d+)x(\d+)\].*?" ; Series Name [1x2] Episode Name
    #".*(\d+)x(\d+).*?" ; Series.Name.1x01.Episode.Name
  ])

(defn -main [ & args ])
