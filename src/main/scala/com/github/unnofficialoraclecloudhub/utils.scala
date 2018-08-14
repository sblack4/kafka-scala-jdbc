package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types
import org.apache.spark.sql.Row
import scala.util.Random
import java.time.format.DateTimeFormatter
import java.time.LocalDate


object Utils {

    def handleJson(df: Dataset[Ticket]) = {
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