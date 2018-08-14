#!/bin/bash

spark-submit \
    --class com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main \
    --jars config-1.3.2.jar spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar \
    kafka-spark-jdbc_2.11-1.0.jar 