#!/bin/bash

set -eux

rm -rf target
sbt pushRemoteCache
rm -rf target
sbt -Dsbt.coursier.home=my_coursier_home pullRemoteCache compile
