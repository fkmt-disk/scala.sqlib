package sqlib.core

private[core] object LogicalOperator {
  
  object And extends LogicalOperator("and") with WhereClause
  
  object Or extends LogicalOperator("or") with WhereClause
  
}

sealed abstract class LogicalOperator(val symbol: String) {
  
  override def toString = symbol
  
}
