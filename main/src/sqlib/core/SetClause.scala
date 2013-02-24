package sqlib.core

final case class SetClause[T] private[core](val name: String, val value: Any)
