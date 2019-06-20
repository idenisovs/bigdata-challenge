#!/bin/bash

TARGET_JAR="./device-simulator/target/scala-2.12/device-sim-0.1.0.jar"

if [[ ! -f ${TARGET_JAR} ]]; then
    echo "Error: Run \"sbt assembly\" first!"
    exit
fi

java -jar ${TARGET_JAR}
