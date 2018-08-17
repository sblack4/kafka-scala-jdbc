package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types
import org.apache.spark.sql.Row


object utils {

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