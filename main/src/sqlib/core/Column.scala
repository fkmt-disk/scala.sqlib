package sqlib.core

import scala.collection.mutable.ListBuffer

private[core] abstract class Column[T <: Table](val sqltype: String) {
  
  val name: String
  
  protected def equalsImpl(x: Any): Unit
  
  override final def equals(x: Any): Boolean = {
    equalsImpl(x)
    false
  }
  
  def and(col: Column[T]): Column[T] = {
    Column addWhereClause LogicalOperator.And
    this
  }
  
  def or(col: Column[T]): Column[T] = {
    Column addWhereClause LogicalOperator.Or
    this
  }
  
}

private[core] object Column {
  
  private[this] val threadlocal = new ThreadLocal[ListBuffer[WhereClause]] {
    override def initialValue = ListBuffer()
  }
  
  def addWhereClause(clause: WhereClause): Unit = {
    threadlocal.get += clause
  }
  
  def getWhereClause: List[WhereClause] = {
    val list = threadlocal.get
    threadlocal.remove
    list.toList
  }
  
}