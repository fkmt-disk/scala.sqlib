package sqlib.core

final class NumberColumn[T <: Table](val name: String) extends Column[T]("number") {
  
  override protected def equalsImpl(x: Any) = x match {
    case nil if nil == null =>
      Column addWhereClause Condition("%s is null".format(name), nil)
    case num: Number =>
      Column addWhereClause Condition("%s = ?".format(name), num)
    case _ =>
      throw new IllegalArgumentException(x.toString)
  }
  
  def <>(x: Any): Column[T] = x match {
    case nil if nil == null =>
      Column addWhereClause Condition("%s is not null".format(name), nil)
      this
    case num: Number =>
      Column addWhereClause Condition("%s <> ?".format(name), num)
      this
    case _ =>
      throw new IllegalArgumentException(x.toString)
  }
  
  def <(x: Number): Column[T] = {
    Column addWhereClause Condition("%s < ?".format(name), x)
    this
  }
  
  def <=(x: Number): Column[T] = {
    Column addWhereClause Condition("%s <= ?".format(name), x)
    this
  }
  
  def >(x: Number): Column[T] = {
    Column addWhereClause Condition("%s > ?".format(name), x)
    this
  }
  
  def >=(x: Number): Column[T] = {
    Column addWhereClause Condition("%s >= ?".format(name), x)
    this
  }
  
}