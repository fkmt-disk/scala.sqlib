package sqlib.core.column

import sqlib.core.Condition
import sqlib.core.WhereClause

/**
 * DecimalColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class DecimalColumn[T](
    name: String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) with Compare[T] {
  
  type V = BigDecimal
  
  override protected def equalsImpl(x: Any) = x match {
    case None | _ if x == null =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: BigDecimal =>
      WhereClause.get.buffer += Condition(s"$name = ?", x.asAnyRef)
    case x: Int =>
      equalsImpl(BigDecimal(x))
    case x: Long =>
      equalsImpl(BigDecimal(x))
    case x: Float =>
      equalsImpl(BigDecimal(x))
    case x: Double =>
      equalsImpl(BigDecimal(x))
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: BigDecimal =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", x.asAnyRef)
      clause
    case x: Int =>
      <>(BigDecimal(x))
    case x: Long =>
      <>(BigDecimal(x))
    case x: Float =>
      <>(BigDecimal(x))
    case x: Double =>
      <>(BigDecimal(x))
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
}
