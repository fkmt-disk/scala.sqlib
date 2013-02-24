package sqlib.core.sequel

import java.sql.Connection
import sqlib.core._
import scala.collection.mutable.ListBuffer

final class UpdateSequel[T] private[core] {
  
  private[this] var _set: List[SetClause[T]] = Nil
  
  private[this] var _where: (String, List[Any]) = null
  
  def set(clause: SetClause[T]*): UpdateSequel[T] = {
    _set = clause.toList
    this
  }
  
  def where(where: => WhereClause[T]): UpdateSequel[T] = {
    WhereClause.clear
    _where = where.build
    this
  }
  
  def go(conn: Connection)(implicit manifest: ClassManifest[T]): Int = {
    val klass: Class[_] = manifest.erasure
    
    val buff = new ListBuffer[String]
    
    buff append "update"
    
    buff append klass.getSimpleName.toLowerCase
    
    if (_set.isEmpty == false)
      buff append ("set " +  _set.map( x => "%s = ?".format(x.name) ).mkString(", "))
    
    if (_where != null)
      buff append ("where " + _where._1)
    
    val sql = buff.mkString(" ")
    
    val values = _where match {
      case x if x == null => _set.map(_.value)
      case _ => _set.map(_.value) ++ _where._2
    }
    
    printf("%s; %s%n", sql, values)
    
    // TODO
    
    -1
  }
  
}