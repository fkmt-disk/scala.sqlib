package sqlib.core.sequel

import java.sql.Connection

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

import org.apache.commons.dbutils.QueryRunner

import sqlib.core.WhereClause

/**
 * DeleteSequel.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class DeleteSequel[T] private[core] {
  
  private[this] var _where: (String, List[AnyRef]) = null
  
  def where(where: => WhereClause[T]): DeleteSequel[T] = {
    WhereClause.clear
    _where = where.build
    this
  }
  
  def go(conn: Connection)(implicit tag: ClassTag[T]): Int = {
    import org.apache.commons.dbutils.QueryRunner
    
    val klass = tag.runtimeClass
    
    val buff = new ListBuffer[String]
    
    buff append s"delete from ${klass.getSimpleName.toLowerCase}"
    
    if (_where != null)
      buff append s"where ${_where._1}"
    
    val sql = buff.mkString(" ")
    
    val runner = new QueryRunner
    
    _where match {
      case x if x == null =>
        println(sql)
        runner.update(conn, sql)
      case _ =>
        println("$sql; ${_where._2}")
        runner.update(conn, sql, _where._2:_*)
    }
  }
  
}
