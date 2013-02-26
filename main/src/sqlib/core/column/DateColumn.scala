package sqlib.core.column

import java.util.Date

import sqlib.core._

final class DateColumn[T](name: String, sqltype: Int) extends Column[T](name, sqltype) {
  
  override protected def equalsImpl(x: Any) = x match {
    case x if x == null =>
      WhereClause.get.buffer += Condition(x, "%s is null", name)
    case x: Date =>
      WhereClause.get.buffer += Condition(x, "%s = ?", name)
    case x: String =>
      equalsImpl(Preamble str2date x)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case x if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(x, "%s is not null", name)
      clause
    case x: Date =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(x, "%s <> ?", name)
      clause
    case x: String =>
      <>(Preamble str2date x)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s < ?", name)
    clause
  }
  
  def <=(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s <= ?", name)
    clause
  }
  
  def >(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s > ?", name)
    clause
  }
  
  def >=(x: Date): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s >= ?", name)
    clause
  }
  
}