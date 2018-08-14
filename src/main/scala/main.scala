package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import _root_.kafka.serializer.StringDecoder //http://stackoverflow.com/questions/36397688/sbt-cannot-import-kafka-encoder-decoder-classes
import org.apache.spark.sql._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import com.typesafe.config.ConfigFactory


object Main {
    def main(args: Array[String]) = {

        // val conf = new SparkConf()
        //     .setAppName("Stream to ADWC")
        //     .setMaster("yarn")
        // val sc = new SparkContext(conf)
        val spark = SparkSession
            .builder()
            .appName("Stream to ADWC")
            .config("master", "yarn")
            .getOrCreate()
        val ssc = new StreamingContext(spark.sparkContext, Seconds(5))

        val conff = ConfigFactory.load()
        val topic = conff.getString("app.topic")
        val brokers = conff.getString("app.broker")

        val topicsSet = topic.split(",").toSet
        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

        //https://spark.apache.org/docs/1.6.1/streaming-kafka-integration.html
        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

        //for debugging, you can print the full contents of the first 10 rows of each batch of messages by uncommenting the following
        //messages.print()
        import spark.implicits._

         messages.foreachRDD(rdd => {
            var df = spark.read.json(rdd.map(x => x._2))
        
            try {
                val ds = df.as[classes.Ticket]
                ds.show()
                utils.handleJson(ds)
            } catch {
                case e: Exception =>
                    println(e)
            }
        })

    }
}