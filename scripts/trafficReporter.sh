#!/bin/sh

if [ ! -x $JAR_DIR/TrafficReporter.jar ]; then
  echo Somehow, this program has been installed poorly.
  exit 1
fi

java -cp $JAR_DIR/TrafficReporter.jar  com/basingwerk/xmlthinner/TrafficReporter $@

