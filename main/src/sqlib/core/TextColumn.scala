package sqlib.core

final class TextColumn[T <: Table](val name: String) extends Column[T]("text") {
  
  override protected def equalsImpl(x: Any) = x match {
    case n if n == null =>
      Column addWhereClause Condition("%s is null".format(name), x)
    case s: String =>
      Column addWhereClause Condition("%s = '?'".format(name), x)
    case _ =>
      equalsImpl(x.toString)
  }
  
  def <>(x: Any): Column[T] = x match {
    case n if n == null =>
      Column addWhereClause Condition("%s is not null".format(name), x)
      this
    case s: String =>
      Column addWhereClause Condition("%s <> '?'".format(name), x)
      this
    case _ =>
      Column addWhereClause Condition("%s <> '?'".format(name), x.toString)
      this
  }
  
  def ~(x: String): Column[T] = {
    Column addWhereClause Condition("%s like ('%'||'?'||'%')".format(name), x)
    this
  }
  
}