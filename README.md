# Media Mogul

50% media player with library, 50% excuse to learn Clojure. Make of that what
you will.

The aim is to build a replacement for XBMC, which does far more than I need it
and in doing so fails to do any of it particularly well (apart from the media
library... thats quite nifty). Media Mogul aims to provide a media library and
delegate play back to VLC or FFMPEG or something. We'll see how that goes, I may
end up building an entire player as well. Won't that be fun?

The other aim is to keep things clean enough that other people writing extensions
don't end up tearing out their hair. Again, that may go well, or it may not.

## What can it do

*Disclaimer:* Anything not explicitly marked as done here is something I plan to
have it doing before version 1.0.

### Media Library

This is the core of what Media Mogul does. Running through a directory tree of
films of TV series and categorising them for watching.

* TV series information from thetvdb.com
* Film information from IMDB
* Tracking what you've watched
* Resuming from midway through a partially watched item
* Thumbnails
* Shared libraries for use on multiple devices

### Remote Control Support

* Support for XBMC mobile app remotes
* Support for Apple Remote
* Support for Windows Media Centre remotes
* Keyboard control

### Video Playback

* 5.1 audio output
* Playback of all popular media formats (exactly which depends what player I end up
  using.

### Network Transparency

A single server should be able to share media library details with multiple frontends,
although exactly how that'll be implemented is still very much up in the air.

## Running Media Mogul

*Disclaimer:* This is nowhere near ready for day to day use. Its not even ready for
experimental use. However, if you want to give it a try, here's how:

### "Stable" build

1. Download the latest release from http://github.com/jellybob/media-mogul/downloads
   and extract it somewhere.
2. Run `./run.sh` from the directory it extracted to.

### Running from HEAD

If you want to try the latest development version you can do so like this:

1. Clone the repository `git clone git://github.com/jellybob/media-mogul`
2. Install Leiningen from https://github.com/technomancy/leiningen if you haven't already
3. Execute `lein run` from the root of the checkout, or `lein repl` if you want a REPL to
   play with.

### Creating a release build

To prepare a new release run `build.sh` in the root of the project. This will create
`target/pkg/media-mogul-$VERSION.tar.gz` ready for release wherever you might choose
to do so.

## Whats with the name?

A media mogul is someone who takes all the glory, while at the same time doing as little
as possible. Hence the name.
