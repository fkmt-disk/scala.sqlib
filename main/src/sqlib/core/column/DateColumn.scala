package sqlib.core.column

import java.util.Date

import sqlib.core._

final class DateColumn[T](name: String, sqltype: Int) extends Column[T](name, sqltype) {
  
  def :=(x: String) = SetClause[T](name, x)
  
  def :=(x: Date): SetClause[T] = x match {
    case x if x == null => :=(x)
    case _ => :=("%1$tF %1$tT" format x)
  }
  
  override protected def equalsImpl(x: Any) = x match {
    case x if x == null =>
      WhereClause.get.buffer += Condition(x, "%s is null", name)
    case x: String =>
      WhereClause.get.buffer += Condition(x, "%s = ?", name)
    case x: Date =>
      equalsImpl("%1$tF %1$tT" format x)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case x if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(x, "%s is not null", name)
      clause
    case x: String =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(x, "%s <> ?", name)
      clause
    case x: Date =>
      <>("%1$tF %1$tT" format x)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <(x: Date): WhereClause[T] = {
    <("%1$tF %1$tT" format x)
  }
  
  def <(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s < ?", name)
    clause
  }
  
  def <=(x: Date): WhereClause[T] = {
    <=("%1$tF %1$tT" format x)
  }
  
  def <=(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s <= ?", name)
    clause
  }
  
  def >(x: Date): WhereClause[T] = {
    >("%1$tF %1$tT" format x)
  }
  
  def >(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s > ?", name)
    clause
  }
  
  def >=(x: Date): WhereClause[T] = {
    >=("%1$tF %1$tT" format x)
  }
  
  def >=(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s >= ?", name)
    clause
  }
  
}