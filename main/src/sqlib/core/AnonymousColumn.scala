package sqlib.core

class AnonymousColumn[T <: Table](override val name: String = "anonymous") extends Column[T]("anonymous") {
  
  override protected def equalsImpl(x: Any) {
    throw new UnsupportedOperationException
  }
  
}