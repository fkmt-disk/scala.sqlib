package sqlib.core.sequel

import java.sql.Connection

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

import org.apache.commons.dbutils.QueryRunner

import sqlib.core.EntityInfo
import sqlib.core.SetClause
import sqlib.core.WhereClause

/**
 * UpdateSequel.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class UpdateSequel[T] private[core] {
  
  private[this] var _set: List[SetClause[T]] = Nil
  
  private[this] var _where: (String, List[AnyRef]) = null
  
  def set(clause: SetClause[T]*): UpdateSequel[T] = {
    _set = clause.toList
    this
  }
  
  def where(where: => WhereClause[T]): UpdateSequel[T] = {
    WhereClause.clear
    _where = where.build
    this
  }
  
  def go(conn: Connection)(implicit tag: ClassTag[T]): Int = {
    import org.apache.commons.dbutils.QueryRunner
    
    val klass = tag.runtimeClass
    
    val buff = new ListBuffer[String]
    
    buff append s"update ${klass.getAnnotation(classOf[EntityInfo]).name}"
    
    if (_set.isEmpty == false)
      buff append s"set ${_set.map( x => "%s = ?".format(x.name) ).mkString(", ")}"
    
    if (_where != null)
      buff append s"where ${_where._1}"
    
    val sql = buff.mkString(" ")
    
    val values = _where match {
      case x if x == null => _set.map(_.value)
      case _ => _set.map(_.value) ++ _where._2
    }
    
    println(s"$sql; ${values}")
    
    new QueryRunner().update(conn, sql, values:_*)
  }
  
}
