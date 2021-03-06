package sqlib.core

/**
 * Bracket.
 * 
 * @author fkmt.disk@gmail.com
 */
private[core] object Bracket {
  
  object begin extends Bracket("(")
  
  object term extends Bracket(")")
  
}

sealed abstract class Bracket(val symbol: String) {
  
  override def toString = symbol
  
}
