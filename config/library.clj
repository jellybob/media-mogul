{
  :path "/Volumes/shared/TV"
  :supported-file-types #{"mp4" "mkv" "avi"}
  :blacklisted-paths [
    #".*/\..*"      ; Hidden files and directories
    #".*\.sample.*" ; Samples
  ]
  :metadata-matchers [
    #".*s(\d+)e(\d+).*"     ; Series Name - s01e02 - Episode Name
    #".*s(\d+)e(\d+).*"     ; Series Name - s01_e02 - Episode Name
    #".*\.s(\d+)e(\d+).*?"  ; Series.Name-s01e02-Episode.Name
    #".*\[(\d+)x(\d+)\].*?" ; Series Name [1x2] Episode Name
    #".*(\d+)x(\d+).*?"     ; Series.Name.1x01.Episode.Name
  ]
}
