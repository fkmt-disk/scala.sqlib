package sqlib.core

import collection.mutable.ListBuffer
import annotation.tailrec

class WhereClause[T <: Table] private[core]() {

  private[core] val buffer = new ListBuffer[SqlParts]

  def and(clause: WhereClause[T]): WhereClause[T] = {
    buffer += LogicalOperator.And
    this
  }

  def or(clause: WhereClause[T]): WhereClause[T] = {
    buffer += LogicalOperator.Or
    this
  }

  private[core] def mksql: (String, List[Any]) = {
    val sortedClauses = sort(new ListBuffer[ListBuffer[SqlParts]], buffer).toList
    var params = new ListBuffer[Any]
    val sql = sortedClauses.map { clause =>
      clause match {
        case x: Bracket =>
          x.toString
        case x: LogicalOperator =>
          x.toString
        case x: Condition =>
          params += x.value
          x.clause
        case _ =>
          sys.error("unknown clause type: %s" format clause)
      }
    }
    (sql.mkString(" "), params.toList)
  }

  @tailrec
  private[this] def sort(buffer: ListBuffer[ListBuffer[SqlParts]], list: Seq[SqlParts]): ListBuffer[SqlParts] = {
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

  private[this] val threadlocal = new ThreadLocal[WhereClause[_]] {
    override def initialValue = null
  }

  def get[T <: Table]: WhereClause[T] = {
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
