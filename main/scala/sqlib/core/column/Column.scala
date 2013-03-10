package sqlib.core.column

import sqlib.core.SortOrder
import sqlib.core.AsAnyRef

/**
 * Column.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
abstract class Column[T] private[core](
    val name: String,
    val ordinal: Int,
    val sqltype: Int
) extends AsAnyRef {
  
  protected def equalsImpl(x: Any): Unit
  
  override final def equals(x: Any): Boolean = {
    equalsImpl(x)
    false
  }
  
  final def asc: SortOrder[T] = SortOrder.asc(name)
  
  final def desc: SortOrder[T] = SortOrder.desc(name)
  
}
