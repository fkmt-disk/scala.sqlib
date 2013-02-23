package sqlib.core

final class TextColumn[T <: Table](name: String) extends Column[T]("text") {
  
  override protected def equalsImpl(x: Any) = x match {
    case nil if nil == null =>
      WhereClause.get.buffer += Condition("%s is null".format(name), nil)
    case str: String =>
      WhereClause.get.buffer += Condition("%s = '?'".format(name), str)
    case _ =>
      equalsImpl(x.toString)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case nil if nil == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition("%s is not null".format(name), nil)
      clause
    case str: String =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition("%s <> '?'".format(name), str)
      clause
    case _ =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer += Condition("%s <> '?'".format(name), x.toString)
      clause
  }
  
  def ~(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition("%s like ('%%' || ? || '%%')".format(name), x.toString)
    clause
  }
  
}