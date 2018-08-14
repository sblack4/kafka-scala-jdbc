package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import _root_.kafka.serializer.StringDecoder //http://stackoverflow.com/questions/36397688/sbt-cannot-import-kafka-encoder-decoder-classes
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import com.typesafe.config._
import app._


object Main {
    def main(args: Args[String]){

        val conf = new SparkConf()
            .setAppName("Stream to ADWC")
            .setMaster("yarn")
        val sc = new SparkContext(conf)
        val ssc = new StreamingContext(sc, Seconds(5))

        val conff = ConfigFactory.load()
        val topic = conff.getString("app.topic")
        val brokers = conff.getString("app.broker")

        val topicsSet = topic.split(",").toSet
        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

        println("Creating Kafka DStream")
        //https://spark.apache.org/docs/1.6.1/streaming-kafka-integration.html
        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
        println("Setting up operations on DStream")    
        //for debugging, you can print the full contents of the first 10 rows of each batch of messages by uncommenting the following
        //messages.print()

        messages.foreachRDD(rdd => {
           var df=sqlContext.read.json(rdd.map(x => x._2))
           df.show()
           // var df=sqlContext.createDataFrame(rdd)
           println(df.getClass.getSimpleName)

        })

    }
}