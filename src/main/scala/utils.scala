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

        tickets.filter("id IS NOT null")
            .write.mode("append")
            .insertInto("default.tickets")
    }

}