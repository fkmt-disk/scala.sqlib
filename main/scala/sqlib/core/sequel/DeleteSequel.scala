package sqlib.core.sequel

import java.sql.Connection
import sqlib.core._
import scala.collection.mutable.ListBuffer

final class DeleteSequel[T] private[core] {
  
  private[this] var _where: (String, List[Any]) = null
  
  def where(where: => WhereClause[T]): DeleteSequel[T] = {
    WhereClause.clear
    _where = where.build
    this
  }
  
  def go(conn: Connection)(implicit manifest: ClassManifest[T]): Int = {
    val klass: Class[_] = manifest.erasure
    
    val buff = new ListBuffer[String]
    
    buff append "delete from"
    
    buff append klass.getSimpleName.toLowerCase
    
    if (_where != null)
      buff append ("where " + _where._1)
    
    val sql = buff.mkString(" ")
    
    _where match {
      case x if x == null => println(sql)
      case _ => printf("%s; %s%n", sql, _where._2)
    }
    
    // TODO
    
    -1
  }
  
}