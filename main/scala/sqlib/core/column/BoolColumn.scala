package sqlib.core.column

import sqlib.core.WhereClause
import sqlib.core.Condition

/**
 * BoolColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class BoolColumn[T](
    name: String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) {
  
  override protected def equalsImpl(x: Any) = x match {
    case None | _ if x == null =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: Boolean if !x =>
      WhereClause.get.buffer += Condition(s"$name = ?", java.lang.Boolean.FALSE)
    case x: Boolean if x =>
      WhereClause.get.buffer += Condition(s"$name = ?", java.lang.Boolean.TRUE)
    case x: Int if x == 0 =>
      equalsImpl(false)
    case x: Int if x == 1 =>
      equalsImpl(true)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: Boolean if !x =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", java.lang.Boolean.FALSE)
      clause
    case x: Boolean if x =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", java.lang.Boolean.TRUE)
      clause
    case x: Int if x == 0 =>
      <>(false)
    case x: Int if x == 1 =>
      <>(true)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
}
