package sqlib.core

import java.text.{SimpleDateFormat => SDF}

/**
 * Utils.
 * 
 * @author fkmt.disk@gmail.com
 */
private[core] object Utils {
  
  private[this] val yyyyMMdd = raw"(\d{4}-\d{2}-\d{2})".r
  private[this] val yyyyMMdd_HHmmss = raw"(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})".r
  private[this] val yyyyMMdd_HHmmss_SSS = raw"(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d{3})".r
  
  def str2date(x: String): java.util.Date = x match {
    case yyyyMMdd(x) => new SDF("yyyy-MM-dd").parse(x)
    case yyyyMMdd_HHmmss(x) => new SDF("yyyy-MM-dd HH:mm:ss").parse(x)
    case yyyyMMdd_HHmmss_SSS(x) => new SDF("yyyy-MM-dd HH:mm:ss.SSS").parse(x)
    case _ => throw new IllegalArgumentException(x)
  }
  
}
