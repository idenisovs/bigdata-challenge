#!/bin/bash

export JAVA_HOME=/usr/local/lib/java8
export PATH="$JAVA_HOME/bin":$PATH
export SPARK_DIST_CLASSPATH=$(hadoop classpath)

MASTER="local[2]"

spark-submit --class lv.edreams.bdc.job.Main \
--packages org.apache.spark:spark-streaming-kafka-0-10_2.12:2.4.3 \
--master ${MASTER} \
./process-job/target/scala-2.12/process-job-0.1.0.jar
