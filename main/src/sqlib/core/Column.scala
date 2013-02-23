package sqlib.core

private[core] abstract class Column[T <: Table](val name: String) {

  protected def equalsImpl(x: Any): Unit
  
  override final def equals(x: Any): Boolean = {
    equalsImpl(x)
    false
  }
  
}
