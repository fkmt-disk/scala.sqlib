package sqlib.tools

import java.util.Properties

/**
 * Config.
 * 
 * @author fkmt.disk@gmail.com
 */
object Config {
  
  private[this] val resource_name = "sqlib.properties"
  
  private[this] val conf = {
    val conf = new Properties
    
    val resource = getClass.getClassLoader.getResourceAsStream(s"/$resource_name") match {
      case res if res == null =>
        ClassLoader.getSystemResourceAsStream(resource_name)
      case res =>
        res
    }
    
    if (resource != null) {
      try {
        conf.load(resource)
      }
      finally {
        resource.close()
      }
    }
    else
      println(s"$resource_name not found")
    
    new Props(conf)
  }
  
  private[this] class Props(p: Properties) {
    def get(k: String): Option[String] = {
      val v = p.getProperty(k, "").trim
      if (v.length == 0)
        None
      else
        Some(v)
    }
  }
  
  val driver = conf.get("driver")
  
  val url = conf.get("url")
  
  val username = conf.get("username")
  
  val password = conf.get("password")
  
  val connection_factory = conf.get("connection_factory")
  
  val catalog = conf.get("catalog")
  
  val schema = conf.get("schema")
  
  val outdir = conf.get("outdir")
  
  val package_name = conf.get("package_name")
  
  val clean = conf.get("clean").getOrElse(false.toString).toBoolean
  
}
