#!/bin/bash

source ./env.sh

file="."


spark-submit \
    --verbose \
    --master yarn \
    --conf 'spark.executor.extraJavaOptions=-Doracle.jdbc.level=ALL'\
    --conf 'spark.driver.extraJavaOptions=-Doracle.jdbc.level=ALL'\
    --conf 'spark.executor.extraJavaOptions=-Doracle.jdbc.Trace=true'\
    --conf 'spark.driver.extraJavaOptions=-Doracle.jdbc.Trace=true'\
    --conf "spark.executor.extraJavaOptions=-Doracle.net.tns_admin=$file" \
    --conf "spark.driver.extraJavaOptions=-Doracle.net.tns_admin=$file" \
    --conf "spark.executor.extraJavaOptions=-Doracle.net.wallet_location=$file" \
    --conf "spark.driver.extraJavaOptions=-Doracle.net.wallet_location=$file" \
    --files file:///home/spark/wallet_LUIGI/tnsnames.ora \
    --files file:///home/spark/wallet_LUIGI/cwallet.sso \
    --files file:///home/spark/wallet_LUIGI/ewallet.p12 \
    --files file:///home/spark/wallet_LUIGI/keystore.jks \
    --files file:///home/spark/wallet_LUIGI/ojdbc.properties \
    --files file:///home/spark/wallet_LUIGI/sqlnet.ora \
    --files file:///home/spark/wallet_LUIGI/truststore.jks \
    --jars file:///home/spark/lib/ojdbc8_g.jar \
    --jars file:///home/spark/lib/oraclepki.jar \
    --jars file:///home/spark/lib/osdt_cert.jar \
    --jars file:///home/spark/lib/osdt_core.jar \
    --jars file:///home/spark/lib/scala-library-2.11.8.jar \
    --jars file:///home/spark/lib/spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar \
    --class "com.github.unofficialoraclecloudhub.kafkasparkjdbc.Main" \
    file:///home/spark/kafka-spark-jdbc_2.11-1.0.jar ${broker} ${topic} ${adwc_user} ${adwc_pwd} ${adwc_conn}
