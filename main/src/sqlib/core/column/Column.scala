package sqlib.core.column

import sqlib.core.SortOrder

private[core] abstract class Column[T](val name: String, val sqltype: Int) {
  
  protected def equalsImpl(x: Any): Unit
  
  override final def equals(x: Any): Boolean = {
    equalsImpl(x)
    false
  }
  
  final def asc: SortOrder[T] = SortOrder.asc(name)
  
  final def desc: SortOrder[T] = SortOrder.desc(name)
  
}
