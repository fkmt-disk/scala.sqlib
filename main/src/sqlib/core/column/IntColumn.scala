package sqlib.core.column

import sqlib.core._

final class IntColumn[T](name: String, sqltype: Int) extends Column[T](name, sqltype) {
  
  def :=(x: Int) = SetClause[T](name, x)
  
  def :=(x: Option[Int]) = x match {
    case Some(i) => SetClause[T](name, i)
    case None => SetClause[T](name, null)
  }
  
  override protected def equalsImpl(x: Any) = x match {
    case x if x == null =>
      WhereClause.get.buffer += Condition(x, "%s is null", name)
    case x: Int =>
      WhereClause.get.buffer += Condition(x, "%s = ?", name)
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case x if x == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(x, "%s is not null", name)
      clause
    case x: Int =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition(x, "%s <> ?",name)
      clause
    case _ =>
      throw new IllegalArgumentException(String valueOf x)
  }
  
  def <(x: Int): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s < ?", name)
    clause
  }
  
  def <=(x: Int): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s <= ?", name)
    clause
  }
  
  def >(x: Int): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s > ?", name)
    clause
  }
  
  def >=(x: Int): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x, "%s >= ?", name)
    clause
  }
  
}
