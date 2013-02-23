package sqlib.core

private[core] object LogicalOperator {
  
  object And extends LogicalOperator("and") with SqlParts
  
  object Or extends LogicalOperator("or") with SqlParts
  
}

sealed abstract class LogicalOperator(val symbol: String) {
  
  override def toString = symbol
  
}
