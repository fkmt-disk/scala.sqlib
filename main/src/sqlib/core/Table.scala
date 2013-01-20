package sqlib.core

import scala.annotation.tailrec
import scala.collection.mutable.{ ListBuffer => Buffer }

abstract class Table {
  
  type T <: Table

  final def select(where: => Column[T])(implicit c: ClassManifest[T]): List[T] = {
    where

    val clauses = Column.getWhereClause

    val sql = mksql(clauses)

    println("sql=%s" format sql._1)
    println("params=%s" format sql._2)

    val klass = c.erasure
    /*
    val constructor = t.erasure.getConstructor(Array[Class](classOf[ResultSet]))
    val table = constructor.newInstance
    */
    println(klass)

    List()
  }

  //protected def newInstance(rset) = T

  implicit final def bool2column(bool: Boolean): Column[T] = {
    if (bool)
      throw new UnsupportedOperationException("`!=` is unsupported; must be use `<>`")
    new AnonymousColumn[T]
  }

  @tailrec
  private def sort(buffer: Buffer[Buffer[WhereClause]], list: Seq[WhereClause]): Buffer[WhereClause] = {
    list match {
      case Seq(cond0: Condition, cond1: Condition, _*) =>
        buffer append Buffer(Bracket.begin, list.head)
        sort(buffer, list.tail)
      case Seq(cond: Condition, ope: LogicalOperator, rest @ _*) =>
        buffer.last.append(ope, cond)
        sort(buffer, rest)
      case Seq(ope: LogicalOperator, rest @ _*) =>
        buffer.last.prepend(list.head)
        buffer.last.append(Bracket.term)
        sort(buffer, rest)
      case Seq(cond: Condition, rest @ _*) =>
        buffer append Buffer(Bracket.begin, cond)
        sort(buffer, rest)
      case Seq() =>
        buffer.last.append(Bracket.term)
        buffer.flatten
      case _ =>
        sys.error("sort failed: buffer=%s, list=%s" format (buffer, list))
    }
  }
  
  private def mksql(clauses: List[WhereClause]): (String, List[Any]) = {
    val sortedClauses = sort(new Buffer[Buffer[WhereClause]], clauses).toList
    var params = new Buffer[Any]
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
  
}
