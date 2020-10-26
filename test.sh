#!/bin/bash

set -eux

rm -rf target
sbt "show Compile / scalacOptions" pushRemoteCache
rm -rf target
sbt -Dsbt.coursier.home=my_coursier_home "show Compile / scalacOptions" pullRemoteCache compile
