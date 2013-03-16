package sqlib.core.sequel

import java.sql.Connection

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

import org.apache.commons.dbutils.QueryRunner

import sqlib.core.EntityInfo
import sqlib.core.SortOrder
import sqlib.core.WhereClause
import sqlib.core.column.Column
import sqlib.dbutils.RowResultSetHandler

/**
 * SelectSequel.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class SelectSequel[T] private[core](columns: Column[T]*) {
  
  private[this] var _columns: List[Column[T]] = columns.toList
  
  private[this] var _distinct: Boolean = false
  
  private[this] var _where: (String, List[AnyRef]) = null
  
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
  
  def go(conn: Connection)(implicit tag: ClassTag[T]): List[T] = {
    val klass  = tag.runtimeClass.asInstanceOf[Class[T]]
    
    val buff = new ListBuffer[String]
    
    buff append "select"
    
    if (_distinct)
      buff append "distinct"
    
    if (_columns.isEmpty)
      buff append "*"
    else
      buff append _columns.map(_.name).mkString(", ")
    
    buff append s"from ${klass.getAnnotation(classOf[EntityInfo]).name}"
    
    if (_where != null)
      buff append s"where ${_where._1}"
    
    if (_orders != null)
      buff append s"order by ${_orders.map(_.clause).mkString(", ")}"
    
    val sql = buff.mkString(" ")
    
    val runner = new QueryRunner
    val rsh = new RowResultSetHandler[T]
    
    _where match {
      case x if x == null =>
        println(sql)
        runner.query(conn, sql, rsh)
      case _ =>
        println(s"$sql; ${_where._2}")
        runner.query(conn, sql, rsh, _where._2:_*)
    }
  }
  
}
