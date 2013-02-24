package jdbc

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

import org.apache.derby.jdbc.EmbeddedDataSource

object DerbyDataSource {
  
  def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    try { main(resource) }
    finally { resource.close() }
  }
  
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
