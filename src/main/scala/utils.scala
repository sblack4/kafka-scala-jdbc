package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types
import org.apache.spark.sql.Row
import java.io._
import org.apache.log4j.{Level, LogManager, PropertyConfigurator}


object utils {


    def writeSqlnet(walletLocal: String) {
        @transient lazy val log = org.apache.log4j.LogManager.getLogger("myLogger")
        val sqlnet = s"""WALLET_LOCATION = (SOURCE = (METHOD = file) (METHOD_DATA = (DIRECTORY="$walletLocal")))
SSL_SERVER_DN_MATCH=yes
        """

        try {
        val pw = new PrintWriter(new File(s"file://$walletLocal/sqlnet.ora"))
        pw.write(sqlnet)
        pw.close()

        } catch {
            case e: FileNotFoundException => log.info(e)
        }
    }

    def handleJson(df: Dataset[classes.Ticket]) = {
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

            // database.write(query)

        })
    }

}