package sqlib.core

final class SortOrder[T] private[SortOrder](name: String, direction: String) {
  
  val clause: String = "%s %s".format(name, direction)
  
}

object SortOrder {
  
  private[core] def asc[T](name: String) = new SortOrder[T](name, "asc")
  
  private[core] def desc[T](name: String) = new SortOrder[T](name, "desc")
  
}
