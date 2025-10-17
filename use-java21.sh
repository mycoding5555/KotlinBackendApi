#!/bin/bash
# Script to set JAVA_HOME to Java 21 for this project

export JAVA_HOME=$(/usr/libexec/java_home -v 21)
echo "JAVA_HOME set to: $JAVA_HOME"
echo "Java version:"
java -version

# If arguments are provided, run them with Java 21
if [ $# -gt 0 ]; then
    exec "$@"
fi