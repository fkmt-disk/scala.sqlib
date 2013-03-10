package sqlib.core.column

import sqlib.core.WhereClause
import sqlib.core.Condition
import sqlib.core.AsAnyRef

/**
 * Compare.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
private[column] trait Compare[T] extends AsAnyRef {
  
  type V
  
  val name: String
  
  def <(x: V): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name < ?", x.asAnyRef)
    clause
  }
  
  def >(x: V): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name > ?", x.asAnyRef)
    clause
  }
  
  def <=(x: V): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name <= ?", x.asAnyRef)
    clause
  }
  
  def >=(x: V): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name >= ?", x.asAnyRef)
    clause
  }
  
}
