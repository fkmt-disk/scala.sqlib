package sqlib.core

private[core] object LogicalOperator {
  
  object And extends LogicalOperator("and")
  
  object Or extends LogicalOperator("or")
  
}

sealed abstract class LogicalOperator(val symbol: String) {
  
  override def toString = symbol
  
}
