package sqlib.core.column

import sqlib.core._

final class TextColumn[T](name:String, sqltype: Int) extends Column[T](name, sqltype) {
  
  override protected def equalsImpl(x: Any) = x match {
    case x if x == null =>
      WhereClause.get.buffer += Condition(x, "%s is null", name)
    case None =>
      WhereClause.get.buffer += Condition(x, "%s is null", name)
    case x: String =>
      WhereClause.get.buffer += Condition(x, "%s = ?", name)
    case _ =>
      equalsImpl(x.toString)
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
    case _ =>
      <>(x.toString)
  }
  
  def ~(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition(x.toString, "%s like ('%%' || ? || '%%')", name)
    clause
  }
  
}