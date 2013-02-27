package sqlib.core.sequel

import java.sql.Connection
import sqlib.core._
import sqlib.core.column._
import scala.collection.mutable.ListBuffer

final class SelectSequel[T] private[core](columns: Column[T]*) {
  
  private[this] var _columns: List[Column[T]] = columns.toList
  
  private[this] var _distinct: Boolean = false
  
  private[this] var _where: (String, List[Any]) = null
  
  private[this] var _orders: List[SortOrder[T]] = null
  
  def distinct(): SelectSequel[T] = {
    _distinct = true
    return this
  }
  
  def where(where: => WhereClause[T]): SelectSequel[T] = {
    WhereClause.clear
    _where = where.build
    return this
  }
  
  def orderBy(orders: SortOrder[T]*): SelectSequel[T] = {
    _orders = orders.toList
    return this
  }
  
  def go(conn: Connection)(implicit manifest: ClassManifest[T]): List[T] = {
    val klass: Class[_] = manifest.erasure
    
    val buff = new ListBuffer[String]
    
    buff append "select"
    
    if (_distinct)
      buff append "distinct"
    
    if (_columns.isEmpty)
      buff append "*"
    else
      buff append _columns.map(_.name).mkString(", ")
    
    buff append ("from " + klass.getSimpleName.toLowerCase)
    
    if (_where != null)
      buff append ("where " + _where._1)
    
    if (_orders != null)
      buff append ("order by " + _orders.map(_.clause).mkString(", "))
    
    val sql = buff.mkString(" ")
    
    // TODO
    
    _where match {
      case x if x == null => println(sql)
      case _ => printf("%s; %s%n", sql, _where._2)
    }
    
    return Nil
  }
  
}
