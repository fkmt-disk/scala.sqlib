package test.jdbc

import test.Utils._
import sqlib.tools.ConnectionFactory

class DerbyConnectionFactory extends ConnectionFactory {
  
  import test.jdbc.DerbyConnectionFactory.datasource
  
  def getConnection = datasource.getConnection
  
}

object DerbyConnectionFactory {
  import java.io.File
  import java.sql.DriverManager
  import org.apache.derby.jdbc.EmbeddedDataSource
  
  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() {
      try {
        DriverManager.getConnection("jdbc:derby:;shutdown=true")
      }
      catch {
        case e: Throwable => println(e.getMessage)
      }
    }
  })
  
  private val datasource = {
    val root_dir = new File(System getProperty "user.dir").getCanonicalFile
    val db_path = new File(root_dir, "test/sample.db")
    
    val ds = new EmbeddedDataSource
    
    ds.setDatabaseName(db_path.getCanonicalPath)
    
    println("db_path=" + db_path.getCanonicalPath)
    
    if (db_path.exists() == false)
      ds.setCreateDatabase("create")
    
    using(ds.getConnection) { conn =>
      if (conn.getWarnings != null)
        println(conn.getWarnings)
    }
    
    ds.setCreateDatabase(null)
    
    ds
  }
  
  def getConnection = new DerbyConnectionFactory().getConnection
  
}
