package sqlib.core

private[core] object Bracket {
  
  object begin extends Bracket("(") with WhereClause
  
  object term extends Bracket(")") with WhereClause
  
}

sealed abstract class Bracket(val symbol: String) {
  
  override def toString = symbol
  
}
