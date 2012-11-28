#!/bin/bash
PROJECT=$(grep '(defproject' project.clj | awk '{ print $2 }')
VERSION=$(grep '(defproject' project.clj | awk '{ print $3 }' | sed 's/"//g')
PKG_NAME="$PROJECT-$VERSION"
BASE_PATH="target/release"
RELEASE_PATH="$BASE_PATH/$PKG_NAME"

if [ -d $RELEASE_PATH ]
then
  echo "Cleaning previous release"
  rm -rf $RELEASE_PATH
fi
mkdir -p $RELEASE_PATH

echo "Building uberjar"
lein uberjar 1> /dev/null

echo "Copying jar to release directory"
cp target/$PROJECT-$VERSION-standalone.jar $RELEASE_PATH/$PROJECT.jar

echo "Copying native dependencies"
cp -r native $RELEASE_PATH/native

echo "Creating run script"
cat > $RELEASE_PATH/run.sh <<EOF
#!/bin/sh
java -Djava.library.path=./native -jar ./media-mogul.jar
EOF
chmod +x $RELEASE_PATH/run.sh

echo "Building release tarball"
if [ ! -d target/pkg ]
then
  mkdir -p target/pkg
fi
tar -c -z -C $BASE_PATH -c $PKG_NAME > target/pkg/$PROJECT-$VERSION.tar.gz
