package sqlib.core

import java.util.Date

final class DateColumn[T <: Table](val name: String) extends Column[T]("date") {
  
  override protected def equalsImpl(x: Any) = x match {
    case n if n == null =>
      Column addWhereClause Condition("%s is null".format(name), n)
    case s: String =>
      Column addWhereClause Condition("%s = '?'".format(name), x)
    case d: Date   =>
      equalsImpl("%1$tF %1$tT" format x)
  }
  
  def <>(x: Any): Column[T] = x match {
    case nil if nil == null =>
      Column addWhereClause Condition("%s is not null".format(name), nil)
      this
    case str: String =>
      Column addWhereClause Condition("%s <> '?'".format(name), str)
      this
    case date: Date =>
      <>("%1$tF %1$tT" format date)
    case _ =>
      <>(x.toString)
  }
  
  def <(x: Date): Column[T] = {
    <("%1$tF %1$tT" format x)
  }
  
  def <(x: String): Column[T] = {
    Column addWhereClause Condition("%s < '?'".format(name), x)
    this
  }
  
  def <=(x: Date): Column[T] = {
    <=("%1$tF %1$tT" format x)
  }
  
  def <=(x: String): Column[T] = {
    Column addWhereClause Condition("%s <= '?'".format(name), x)
    this
  }
  
  def >(x: Date): Column[T] = {
    >("%1$tF %1$tT" format x)
  }
  
  def >(x: String): Column[T] = {
    Column addWhereClause Condition("%s > '?'".format(name), x)
    this
  }
  
  def >=(x: Date): Column[T] = {
    >=("%1$tF %1$tT" format x)
  }
  
  def >=(x: String): Column[T] = {
    Column addWhereClause Condition("%s >= '?'".format(name), x)
    this
  }
  
}