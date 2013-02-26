package sqlib.core

import java.text.{ SimpleDateFormat => SDF }

private[core] object Preamble {
  
  implicit private[core] def bool2column[T](x: Boolean): WhereClause[T] = {
    if (x)
      throw new UnsupportedOperationException("`!=` is unsupported; must be use `<>`")
    WhereClause.get
  }
  
  private[this] val yyyyMMdd1 = """(\d{4}/\d{2}/\d{2})""".r
  private[this] val yyyyMMdd2 = """(\d{4}/\d{2}/\d{2})""".r
  private[this] val yyyyMMdd_HHmmss1 = """(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})""".r
  private[this] val yyyyMMdd_HHmmss2 = """(\d{4}/\d{2}/\d{2} \d{2}:\d{2}:\d{2})""".r
  private[this] val yyyyMMdd_HHmmss_SSS1 = """(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d{3})""".r
  private[this] val yyyyMMdd_HHmmss_SSS2 = """(\d{4}/\d{2}/\d{2} \d{2}:\d{2}:\d{2}\.\d{3})""".r
  
  implicit private[core] def str2date(x: String): java.util.Date = x match {
    case yyyyMMdd1(x) => new SDF("yyyy-MM-dd").parse(x)
    case yyyyMMdd2(x) => new SDF("yyyy/MM/dd").parse(x)
    case yyyyMMdd_HHmmss1(x) => new SDF("yyyy-MM-dd HH:mm:ss").parse(x)
    case yyyyMMdd_HHmmss2(x) => new SDF("yyyy/MM/dd HH:mm:ss").parse(x)
    case yyyyMMdd_HHmmss_SSS1(x) => new SDF("yyyy-MM-dd HH:mm:ss.SSS").parse(x)
    case yyyyMMdd_HHmmss_SSS2(x) => new SDF("yyyy/MM/dd HH:mm:ss.SSS").parse(x)
    case _ => throw new IllegalArgumentException(x)
  }
  
  implicit private[core] def int2opt(x: Int): Option[Int] = Some(x)
  
}