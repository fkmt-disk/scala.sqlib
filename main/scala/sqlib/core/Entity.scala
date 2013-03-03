package sqlib.core

import sqlib.core.column.Column

/**
 * Entity.
 * 
 * @author fkmt.disk@gmail.com
 */
abstract class Entity private[core] {
  
  type T
  
  protected def set(column: Column[T], x: AnyRef): SetClause[T] = SetClause(column.name, x)
  
  import scala.language.implicitConversions
  
  implicit final def bool2column(x: Boolean): WhereClause[T] = Utils.bool2column(x)
  
  implicit final def str2date(x: String): java.util.Date = Utils.str2date(x)
  
  implicit final def int2opt(x: Int): Option[Int] = Utils.int2opt(x)
  
  implicit final def int2integer(x: Int): Integer = Utils.int2integer(x)
  
  implicit final class IntegerOption(opt: Option[Int]) {
    def getInteger: Integer = opt match {
      case None => null
      case Some(i) => i.asInstanceOf[Integer]
    }
  }
  
}
