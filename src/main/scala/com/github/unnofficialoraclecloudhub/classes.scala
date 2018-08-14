package com.github.unofficialoraclecloudhub.kafkasparkjdbc

object Classes {
    case class Ticket (
        id: String, 
        date: String,
        category: String, 
        comment: String
    )
}