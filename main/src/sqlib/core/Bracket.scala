package sqlib.core

private[core] object Bracket {
  
  object begin extends Bracket("(") with SqlParts
  
  object term extends Bracket(")") with SqlParts
  
}

sealed abstract class Bracket(val symbol: String) {
  
  override def toString = symbol
  
}
