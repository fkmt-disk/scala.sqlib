package sqlib.core

private[core] case class Condition(
    clause: String,
    value: Any
) extends SqlParts
