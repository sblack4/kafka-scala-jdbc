package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import _root_.kafka.serializer.StringDecoder //http://stackoverflow.com/questions/36397688/sbt-cannot-import-kafka-encoder-decoder-classes
import org.apache.spark.sql._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._


object Main {
    def main(args: Array[String]) = {

        System.setProperty("oracle.jdbc.Trace", "true")
        System.setProperty("oracle.jdbc.level", "ALL")

        
        val brokers = args(0)
        val topic = args(1)
        val user = args(2)
        val password = args(3)
        val connection = args(4)

        println("--- stuff ---")
        println(brokers)
        println(topic)
        println(user)
        println(connection)

        val query = """
            insert into tickets values ('asdfgafsd','asfdafsd','asdffasd','asffasd')
            """

        database.write(query, connection, user, password)
        // database.setOds(connection, user, password)

        val spark = SparkSession
            .builder()
            .appName("Stream to ADWC")
            .config("master", "yarn")
            .getOrCreate()

        import spark.implicits._
        val ssc = new StreamingContext(spark.sparkContext, Seconds(5))

        val topicsSet = topic.split(",").toSet
        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

        messages.print()

         messages.foreachRDD(rdd => {
            var df = spark.read.json(rdd.map(x => x._2))
        
            // try {
                val ds = df.as[classes.Ticket]
                ds.show()
        val tickets = df.select(
            "id",
            "date",
            "category",
            "comment"
        )

        tickets.foreach(row => {
            val query = s"""
            insert into tickets values ($row.id, $row.date, $row.category, $row.comment)
            """

            database.write(query, connection, user, password)

        })
                // utils.handleJson(ds)
            // } catch {
                // case e: Exception =>
                    // println(e)
            // }
        })

        println("Starting Streaming Context")
        ssc.start()
        ssc.awaitTermination()

    }
}