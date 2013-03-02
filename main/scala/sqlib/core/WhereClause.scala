package sqlib.core

import collection.mutable.ListBuffer
import annotation.tailrec

final class WhereClause[T] private[core] {
  
  private[core] val buffer = new ListBuffer[AnyRef]
  
  def and(clause: WhereClause[T]): WhereClause[T] = {
    buffer append LogicalOperator.And
    this
  }
  
  def or(clause: WhereClause[T]): WhereClause[T] = {
    buffer append LogicalOperator.Or
    this
  }
  
  private[core] def build: (String, List[AnyRef]) = {
    val sortedClauses = sort(new ListBuffer[ListBuffer[AnyRef]], buffer).toList
    val params = new ListBuffer[AnyRef]
    val sql = sortedClauses.map {
      case x: Bracket =>
        x.toString
      case x: LogicalOperator =>
        x.toString
      case x: Condition =>
        params append x.value
        x.clause
      case oth =>
        sys.error("unknown clause type: %s" format oth)
    }
    (sql.mkString(" "), params.toList)
  }
  
  @tailrec
  private[this] def sort(buffer: ListBuffer[ListBuffer[AnyRef]], list: Seq[AnyRef]): ListBuffer[AnyRef] = {
    list match {
      case Seq(cond0: Condition, cond1: Condition, _*) =>
        buffer append ListBuffer(Bracket.begin, list.head)
        sort(buffer, list.tail)
      case Seq(cond: Condition, ope: LogicalOperator, rest @ _*) =>
        buffer.last.append(ope, cond)
        sort(buffer, rest)
      case Seq(ope: LogicalOperator, rest @ _*) =>
        buffer.last.prepend(list.head)
        buffer.last.append(Bracket.term)
        sort(buffer, rest)
      case Seq(cond: Condition, rest @ _*) =>
        buffer append ListBuffer(Bracket.begin, cond)
        sort(buffer, rest)
      case Seq() =>
        buffer.last.append(Bracket.term)
        buffer.flatten
      case _ =>
        sys.error("sort failed: buffer=%s, list=%s" format (buffer, list))
    }
  }
  
}

private[core] object WhereClause {
  
  private[this] val threadlocal = new ThreadLocal[WhereClause[_]]
  
  def get[T]: WhereClause[T] = {
    var clause = threadlocal.get.asInstanceOf[WhereClause[T]]
    if (clause == null) {
      clause = new WhereClause[T]
      threadlocal set clause
    }
    return clause
  }
  
  def clear {
    threadlocal.remove
  }
  
}
