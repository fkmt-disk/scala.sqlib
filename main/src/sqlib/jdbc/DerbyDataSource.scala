package sqlib.jdbc

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

import org.apache.derby.jdbc.EmbeddedDataSource

import sqlib.Utils.using

object DerbyDataSource {
  
  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() {
      try {
        DriverManager.getConnection("jdbc:derby:;shutdown=true")
      }
      catch {
        case e => println(e.getMessage)
      }
    }
  })
  
  val dbpath = new File(System getProperty "user.dir", "sample.db")
  
  val datasource = new EmbeddedDataSource
  
  datasource.setDatabaseName(dbpath.getCanonicalPath)
  
  if (dbpath.exists() == false)
    datasource.setCreateDatabase("create")
  
  using(datasource.getConnection) { conn: Connection =>
    if (conn.getWarnings != null)
      println(conn.getWarnings)
  }
  
  datasource.setCreateDatabase(null)
  
  def getConnection = datasource.getConnection
  
}
