package sqlib.core.column

import sqlib.core.Condition
import sqlib.core.WhereClause

/**
 * IntColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class IntColumn[T](
    name: String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) with Compare[T] {
  
  type V = Int
  
  override protected def equalsImpl(x: Any) = x match {
    case x if x == null || x == None =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: Int =>
      WhereClause.get.buffer += Condition(s"$name = ?", x.asAnyRef)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: Int =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", x.asAnyRef)
      clause
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
}
