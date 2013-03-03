package sqlib.core.column

import java.util.Date

import sqlib.core._

/**
 * DateColumn.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class DateColumn[T](
    name: String,
    ordinal: Int,
    sqltype: Int
) extends Column[T](name, ordinal, sqltype) {
  
  override protected def equalsImpl(x: Any) = x match {
    case None | _ if x == null =>
      WhereClause.get.buffer += Condition(s"$name is null", null)
    case x: Date =>
      WhereClause.get.buffer += Condition(s"$name = ?", x)
    case x: String =>
      equalsImpl(Utils str2date x)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case None | _ if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name is not null", null)
      clause
    case x: Date =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(s"$name <> ?", x)
      clause
    case x: String =>
      <>(Utils str2date x)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name < ?", x)
    clause
  }
  
  def <=(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name <= ?", x)
    clause
  }
  
  def >(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name > ?", x)
    clause
  }
  
  def >=(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(s"$name >= ?", x)
    clause
  }
  
}
