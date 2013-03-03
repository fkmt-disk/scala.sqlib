package sqlib.core

/**
 * SortOrder.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final class SortOrder[T] private(name: String, direction: String) {
  
  val clause = s"$name $direction"
  
}

object SortOrder {
  
  private[core] def asc[T](name: String) = new SortOrder[T](name, "asc")
  
  private[core] def desc[T](name: String) = new SortOrder[T](name, "desc")
  
}
