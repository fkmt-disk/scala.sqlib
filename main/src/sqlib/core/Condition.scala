package sqlib.core

private[core] case class Condition(
    val clause: String,
    val value: Any
) extends WhereClause
