package sqlib.core.sequel

import java.sql.Connection

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

import org.apache.commons.dbutils.QueryRunner

import sqlib.core.SetClause

/**
 * InsertSequel.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class InsertSequel[T] private[core] {
  
  private[this] var _values: List[SetClause[T]] = Nil
  
  def values(values: SetClause[T]*): InsertSequel[T] = {
    _values = values.toList
    this
  }
  
  def go(conn: Connection)(implicit tag: ClassTag[T]): Int = {
    import org.apache.commons.dbutils.QueryRunner
    
    if (_values.isEmpty)
      throw new IllegalStateException("values is empty")
    
    val klass = tag.runtimeClass
    
    val buff = new ListBuffer[String]
    
    buff append "insert into"
    
    buff append klass.getSimpleName.toLowerCase
    
    buff append s"(${_values.map(_.name).mkString(", ")})"
    
    buff append "values"
    
    buff append s"(${("?" * _values.length).toCharArray.mkString(", ")})"
    
    val sql = buff.mkString(" ")
    
    val values = _values.map(_.value)
    
    println(s"$sql; $values")
    
    new QueryRunner().update(conn, sql, values:_*)
  }
  
}
