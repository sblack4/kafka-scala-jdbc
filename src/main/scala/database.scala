package com.github.unofficialoraclecloudhub.kafkasparkjdbc

import java.sql._
import java.security.Security
import oracle.jdbc.pool.OracleDataSource
import oracle.security.pki.OraclePKIProvider
import org.apache.spark.SparkFiles
import org.apache.spark.sql._
import org.apache.log4j.{Level, LogManager, PropertyConfigurator}


 
object database {
    var ods = new OracleDataSource()

    def setOds(spark: SparkSession, filePath: String, connection: String, user: String, password: String) = {
        @transient lazy val log = org.apache.log4j.LogManager.getLogger("myLogger")

        Security.insertProviderAt(new OraclePKIProvider, 3) 

        // spark.sparkContext.addFile(filePath + "/tnsnames.ora")
        // spark.sparkContext.addFile(filePath + "/cwallet.sso")
        // spark.sparkContext.addFile(filePath + "/ewallet.p12")
        // spark.sparkContext.addFile(filePath + "/keystore.jks")
        // // spark.sparkContext.addFile(filePath + "/ojdbc.properties")
        // spark.sparkContext.addFile(filePath + "/truststore.jks")
        spark.sparkContext.addFile(filePath, true)


        val tnsnames = SparkFiles.get("tnsnames.ora")
        log.warn(tnsnames)
        val localpath = SparkFiles.getRootDirectory()
        log.warn(localpath)

        utils.writeSqlnet(localpath)

        val fullConn = connection + localpath

        log.warn(fullConn)

        // this.ods = new OracleDataSource()
        // this.ods.setURL(fullConn)
        // this.ods.setUser(user)
        // this.ods.setPassword(password)
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
        // return ods
    }
    def write(query: String, connection: String, user: String, password: String) = {
        @transient lazy val log = org.apache.log4j.LogManager.getLogger("myLogger")
        log.info("writing new record")

        // try {
        // Security.insertProviderAt(new OraclePKIProvider, 3) 

        // val ods = new OracleDataSource()
        // ods.setURL(connection)
        // ods.setUser(user)
        // ods.setPassword(password)

            val conn = this.ods.getConnection()
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

