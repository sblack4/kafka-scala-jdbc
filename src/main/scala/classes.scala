package com.github.unofficialoraclecloudhub.kafkasparkjdbc


object classes {
    case class Ticket (
        id: String, 
        date: String,
        category: String, 
        comment: String
    )
}