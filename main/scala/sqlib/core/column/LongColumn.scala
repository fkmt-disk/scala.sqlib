package sqlib.core.column

import sqlib.core.Condition
import sqlib.core.WhereClause

/**
 * LongColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class LongColumn[T](
    name: String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) with Compare[T] {
  
  type V = Long
  
  override protected def equalsImpl(x: Any) = x match {
    case None | _ if x == null =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: Long =>
      WhereClause.get.buffer += Condition(s"$name = ?", x.asAnyRef)
    case x: Int =>
      equalsImpl(x.asInstanceOf[Long])
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: Long =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", x.asAnyRef)
      clause
    case x: Int =>
      <>(x.asInstanceOf[Long])
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
}
