package sqlib.tools

import java.sql.ResultSet

/**
 * EntityGenerator.
 * 
 * @author fkmt.disk@gmail.com
 */
object EntityGenerator {
  import scala.collection.JavaConverters._
  
  private def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    import scala.language.reflectiveCalls
    try {
      main(resource)
    }
    finally {
      resource.close()
    }
  }
  
  private def rset2list[T](rset: ResultSet, bean: Class[T], nameMap: Map[String, String]): List[T] = {
    import org.apache.commons.dbutils._
    (new BasicRowProcessor(new BeanProcessor(nameMap.asJava))).toBeanList(rset, bean).asScala.toList
  }
  
  private val r = """^([0-9a-zA-Z]+)([^0-9a-zA-Z]+)([0-9a-zA-Z]*)$""".r
  
  private def capitalize(x: String): String = x match {
    case x if x == null =>
      ""
    case r(a, b, c) if a.length > 1 =>
      a.substring(0, 1).toUpperCase + a.substring(1) + b + capitalize(c)
    case r(a, b, c) if a.length == 1 =>
      a.toUpperCase + b + capitalize(c)
    case a if a.length > 1 =>
      a.substring(0, 1).toUpperCase + a.substring(1)
    case a if a.length == 1 =>
      a.toUpperCase
    case x =>
      sys.error(s"invalid input string: $x")
  }
  
  private def getTableInfo: List[TableInfo] = {
    import java.sql.DriverManager
    import org.apache.commons.dbutils.DbUtils
    import sqlib.core.{Table, View}
    import Config._
    
    DbUtils.loadDriver(driver.orNull)
    
    val conn = connection_factory match {
      case Some(f) =>
        Class.forName(f).newInstance.asInstanceOf[ConnectionFactory].getConnection
      case None =>
        DriverManager.getConnection(url.orNull, username.orNull, password.orNull)
    }
    
    using(conn) { conn =>
      val dbmd = conn.getMetaData
      
      println(s"${dbmd.getURL}")
      
      val tables = using(dbmd.getTables(catalog.orNull, schema.orNull, null, Array("TABLE", "VIEW"))) { rset =>
        rset2list(rset, classOf[TableInfo], TableInfo.map)
      }
      
      for (table <- tables; name = table.tableName) {
        table.columns = using(dbmd.getColumns(catalog.orNull, schema.orNull, name, null)) { rset =>
          rset2list(rset, classOf[ColumnInfo], ColumnInfo.map).asJava
        }
        
        for (idx <- 0 until table.columns.size) {
          val col = table.columns.get(idx)
          col.sqlType = new SqlType(col.dataType)
          col.columnName = col.columnName.toLowerCase
        }
        
        table.tableName = table.tableName.toLowerCase
        table.tableClassName = capitalize(table.tableName)
        table.superType = table.tableType.toLowerCase match {
          case "view"   => classOf[View]
          case "table"  => classOf[Table]
        }
      }
      
      tables
    }
  }
  
  private val file_filter = new java.io.FileFilter {
    override def accept(file: java.io.File) = file.isFile
  }
  
  private val dir_filter = new java.io.FileFilter {
    override def accept(file: java.io.File) = file.isDirectory
  }
  
  private def rmdirs(dir: java.io.File): Boolean = {
    var ret = dir.listFiles(file_filter).foldLeft(true)(_ && _.delete)
    dir.listFiles(dir_filter).foldLeft(ret)(_ && rmdirs(_)) && dir.delete
  }
  
  private val tempalte = {
    import java.util.Properties
    import org.apache.velocity.app.Velocity
    import org.apache.velocity.VelocityContext
    import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
    
    val props = new Properties
    props.setProperty("resource.loader", "CP")
    props.setProperty("CP.resource.loader.class", classOf[ClasspathResourceLoader].getName)
    
    Velocity.init(props)
    
    Velocity.getTemplate("sqlib/tools/entity.vm", "utf-8")
  }
  
  def main(args: Array[String]): Unit = {
    import java.io.File
    import java.io.StringWriter
    import org.apache.velocity.VelocityContext
    
    val pkg = Config.package_name match {
      case Some(p)  => p
      case None     => sys.error("package_name must not be empty")
    }
    
    val outdir = Config.outdir match {
      case Some(d)  => new File(new File(d), pkg.replace('.', '/'))
      case None     => new File(new File("."), pkg.replace('.', '/'))
    }
    
    if (outdir.exists && Config.clean && rmdirs(outdir) == false)
      sys.error(s"${outdir.getPath} remove failed")
    
    if (outdir.exists == false && outdir.mkdirs == false)
      sys.error(s"${outdir.getPath} make failed")
    
    getTableInfo.foreach { table =>
      val ctx = new VelocityContext
      ctx.put("pkg", pkg)
      ctx.put("table", table)
      ctx.put("since", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date))
      
      val src = new File(outdir, s"${table.tableClassName}.scala")
      
      import java.io.PrintWriter
      
      using(new PrintWriter(src, "utf-8")) { out =>
        tempalte.merge(ctx, out)
        out.flush
      }
      
      println(s"write to ${src.getPath}")
    }
    
  }
  
}
