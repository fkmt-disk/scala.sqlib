package sqlib.core

final class NumberColumn[T <: Table](name: String) extends Column[T]("number") {
  
  override protected def equalsImpl(x: Any) = x match {
    case nil if nil == null =>
      WhereClause.get.buffer.append(Condition("%s is null".format(name), nil))
    case num: Number =>
      WhereClause.get.buffer.append(Condition("%s = ?".format(name), num))
    case _ =>
      throw new IllegalArgumentException(x.toString)
  }
  
  def <>(x: Any): WhereClause[T] = x match {
    case nil if nil == null =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer.append(Condition("%s is not null".format(name), nil))
      clause
    case num: Number =>
      val clause: WhereClause[T] = WhereClause.get
      clause.buffer.append(Condition("%s <> ?".format(name), num))
      clause
    case _ =>
      throw new IllegalArgumentException(x.toString)
  }
  
  def <(x: Number): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer.append(Condition("%s < ?".format(name), x))
    clause
  }
  
  def <=(x: Number): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer.append(Condition("%s <= ?".format(name), x))
    clause
  }
  
  def >(x: Number): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer.append(Condition("%s > ?".format(name), x))
    clause
  }
  
  def >=(x: Number): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer.append(Condition("%s >= ?".format(name), x))
    clause
  }
  
}