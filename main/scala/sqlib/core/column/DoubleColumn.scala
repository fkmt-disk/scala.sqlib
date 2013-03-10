package sqlib.core.column

import sqlib.core.Condition
import sqlib.core.WhereClause

/**
 * DoubleColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class DoubleColumn[T](
    name: String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) with Compare[T] {
  
  type V = Double
  
  override protected def equalsImpl(x: Any) = x match {
    case None | _ if x == null =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: Double =>
      WhereClause.get.buffer += Condition(s"$name = ?", x.asAnyRef)
    case x: Int =>
      equalsImpl(x.asInstanceOf[Double])
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: Double =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", x.asAnyRef)
      clause
    case x: Int =>
      <>(x.asInstanceOf[Double])
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
}
