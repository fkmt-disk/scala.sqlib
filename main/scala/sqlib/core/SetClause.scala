package sqlib.core

/**
 * SetClause.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
final case class SetClause[T] private[core](val name: String, val value: AnyRef)
