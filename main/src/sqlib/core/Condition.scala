package sqlib.core

private[core] case class Condition(
    assign: Any,
    format: String,
    args: Any*
) {
  
  val clause: String = format.format(args:_*)
  
  val value: Any = assign
  
}
