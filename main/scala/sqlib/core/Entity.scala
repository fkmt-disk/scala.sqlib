package sqlib.core

import sqlib.core.column.Column

/**
 * Entity.
 * 
 * @author fkmt.disk@gmail.com
 */
abstract class Entity private[core] extends AsAnyRef {
  
  type T
  
  protected def set(column: Column[T], x: AnyRef): SetClause[T] = SetClause(column.name, x)
  
  import scala.language.implicitConversions
  
  implicit final def bool2column(x: Boolean): WhereClause[T] = {
    if (x)
      throw new UnsupportedOperationException("`!=` is unsupported; must be use `<>`")
    WhereClause.get
  }
  
  implicit final def str2date(x: String): java.util.Date = Utils.str2date(x)
  
  implicit final def asAnyRef(x: AnyVal) = x.asAnyRef
  
}
