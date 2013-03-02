package sqlib.core.column

import sqlib.core.SortOrder

abstract class Column[T] private[core](val name: String, val ordinal: Int, val sqltype: Int) {
  
  protected def equalsImpl(x: Any): Unit
  
  override final def equals(x: Any): Boolean = {
    equalsImpl(x)
    false
  }
  
  final def asc: SortOrder[T] = SortOrder.asc(name)
  
  final def desc: SortOrder[T] = SortOrder.desc(name)
  
}
