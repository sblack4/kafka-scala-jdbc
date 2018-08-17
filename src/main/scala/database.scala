package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import java.sql._
import java.security.Security
import oracle.jdbc.pool.OracleDataSource
import oracle.security.pki.OraclePKIProvider
 
object database {
    // val ods = new OracleDataSource()

    // def setOds(connection: String, user: String, password: String) = {
    //     Security.insertProviderAt(new OraclePKIProvider, 3) 

    //     val ods = new OracleDataSource()
    //     ods.setURL(connection)
    //     ods.setUser(user)
    //     ods.setPassword(password)
    //     return ods
    // }
    def write(query: String, connection: String, user: String, password: String) = {

        // try {
        Security.insertProviderAt(new OraclePKIProvider, 3) 

        val ods = new OracleDataSource()
        ods.setURL(connection)
        ods.setUser(user)
        ods.setPassword(password)
            val conn = ods.getConnection()
            val stmt = conn.createStatement()

            val rset = stmt.executeQuery(query)

            rset.close()
            stmt.close()
            conn.close()
        // } catch {
        //     case e: SQLException => {
        //         println("Connection Failed")
        //         println(e.getStackTrace)
        //     }
        // }
    }
}

