#!/usr/bin/env bash
echo "make sure JAVA_HOME is set to JDK folder"
echo "make sure maven is on the system PATH"
mvn "$@" package dependency:copy-dependencies surefire-report:report -Daggregate=true checkstyle:checkstyle-aggregate 

# NOTE:
# export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64/"