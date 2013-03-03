package sqlib.core.column

import sqlib.core.Condition
import sqlib.core.WhereClause

/**
 * TextColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class TextColumn[T](
    name:String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) {
  
  override protected def equalsImpl(x: Any) = x match {
    case None | _ if x == null =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: String =>
      WhereClause.get.buffer += Condition(s"$name = ?", x)
    case _ =>
      equalsImpl(x.toString)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: String =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", x)
      clause
    case _ =>
      <>(x.toString)
  }
  
  def ~(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name like ('%%' || ? || '%%')", x)
    clause
  }
  
}
