package sqlib.core

import java.util.Date

final class DateColumn[T <: Table](name: String) extends Column[T]("date") {

  override protected def equalsImpl(x: Any) = x match {
    case nil if nil == null =>
      WhereClause.get.buffer += Condition("%s is null".format(name), nil)
    case str: String =>
      WhereClause.get.buffer += Condition("%s = '?'".format(name), str)
    case dt: Date =>
      equalsImpl("%1$tF %1$tT" format dt)
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
    case dt: Date =>
      <>("%1$tF %1$tT" format dt)
    case _ =>
      <>(x.toString)
  }
  
  def <(x: Date): WhereClause[T] = {
    <("%1$tF %1$tT" format x)
  }
  
  def <(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition("%s < '?'".format(name), x)
    clause
  }
  
  def <=(x: Date): WhereClause[T] = {
    <=("%1$tF %1$tT" format x)
  }
  
  def <=(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition("%s <= '?'".format(name), x)
    clause
  }
  
  def >(x: Date): WhereClause[T] = {
    >("%1$tF %1$tT" format x)
  }
  
  def >(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition("%s > '?'".format(name), x)
    clause
  }
  
  def >=(x: Date): WhereClause[T] = {
    >=("%1$tF %1$tT" format x)
  }
  
  def >=(x: String): WhereClause[T] = {
    val clause: WhereClause[T] = WhereClause.get
    clause.buffer += Condition("%s >= '?'".format(name), x)
    clause
  }
  
}