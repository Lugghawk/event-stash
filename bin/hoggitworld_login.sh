#!/bin/bash
set -e

echo "Checking login status of build.hoggitworld.com"
if ! grep -q "build.hoggitworld.com" ~/.docker/config.json ; then
    echo "Authentication not found. Please login:"
    # Can't get interactive terminal in SBT.
    # Disable this for now, and just return a non-zero code that we can pickup in sbt
    #docker login "build.hoggitworld.com"
    exit 1
else
  exit 0
fi

