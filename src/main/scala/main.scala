package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import _root_.kafka.serializer.StringDecoder //http://stackoverflow.com/questions/36397688/sbt-cannot-import-kafka-encoder-decoder-classes
import org.apache.spark.sql._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

import java.sql._
import java.security.Security
import oracle.jdbc.pool.OracleDataSource
import oracle.security.pki.OraclePKIProvider
import org.apache.spark.SparkFiles
import org.apache.spark.sql._
import org.apache.log4j.{Level, LogManager, PropertyConfigurator}


object Main {
    def main(args: scala.Array[String]) = {

        @transient lazy val log = org.apache.log4j.LogManager.getLogger("myLogger")
        
        val brokers = args(0)
        val topic = args(1)
        val user = args(2)
        val password = args(3)
        val connection = args(4)
        val filePath = args(5)

        val spark = SparkSession
            .builder()
            .appName("Stream to ADWC")
            .config("master", "yarn")
            .getOrCreate()


        // DB TEST 

        // get local directory on executor
        val tnsnames = SparkFiles.get("tnsnames.ora")

        val localpath = SparkFiles.getRootDirectory()

        log.warn("SparkFiles.get return type: " + tnsnames.getClass.getSimpleName)

        val fullConn = connection + localpath

        log.warn("ADWC FULL CONNECTION STRING: " + fullConn)

        val ods = new OracleDataSource()
        ods.setURL(fullConn)
        ods.setUser(user)
        ods.setPassword(password)
        log.info("Database Connection done")

         val query = """
            insert into tickets values ('asdfgafsd','asfdafsd','asdffasd','asffasd')
            """       

        val conn = ods.getConnection()
        val stmt = conn.createStatement()

        val rset = stmt.executeQuery(query)

        rset.close()
        stmt.close()
        conn.close()

        // import spark.implicits._
        // val ssc = new StreamingContext(spark.sparkContext, Seconds(5))

        // val topicsSet = topic.split(",").toSet
        // val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
        // val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

        // messages.print()

        //  messages.foreachRDD(rdd => {
        //     var df = spark.read.json(rdd.map(x => x._2))
        
        //     // try {
        //         val ds = df.as[classes.Ticket]
        //         ds.show()
        // val tickets = df.select(
        //     "id",
        //     "date",
        //     "category",
        //     "comment"
        // )

        // tickets.foreach(row => {
        //     val query = s"""
        //     insert into tickets values ($row.id, $row.date, $row.category, $row.comment)
        //     """

        //     database.write(query, connection, user, password)

        // })
        //         // utils.handleJson(ds)
        //     // } catch {
        //         // case e: Exception =>
        //             // println(e)
        //     // }
        // })

        // println("Starting Streaming Context")
        // ssc.start()
        // ssc.awaitTermination()

    }
}