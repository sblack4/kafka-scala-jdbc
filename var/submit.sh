#!/bin/bash


# where we keep the secrets
source ./env.sh

spark-submit \
    --verbose \
    --master yarn \
    --class "com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main" \
    --jars file:///home/spark/lib/ojdbc8.jar \
    --jars file:///home/spark/lib/ojdbc.policy \
    --jars file:///home/spark/lib/oraclepki.jar \
    --jars file:///home/spark/lib/osdt_cert.jar \
    --jars file:///home/spark/lib/osdt_core.jar \
    --jars file:///home/spark/lib/scala-library-2.11.8.jar \
    --jars file:///home/spark/lib/spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar \
    file:///home/spark/kafka-spark-jdbc_2.11-1.0.jar ${broker} ${topic} ${adwc_user} ${adwc_pwd} ${adwc_conn}
