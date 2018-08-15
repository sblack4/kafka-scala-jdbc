#!/bin/bash

spark-submit \
    --master yarn \
    --class "com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main" \
    --jars  file:///home/spark/spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar \
    file:///home/spark/kafka-spark-jdbc_2.11-1.0.jar ${broker} ${topic} ${adwc_user} ${adwc_pwd} ${adwc_conn}
