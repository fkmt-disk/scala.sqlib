package sqlib.core

import sqlib.core.column.Column
import sqlib.core.sequel.SelectSequel

/**
 * View.
 * 
 * @author fkmt.disk@gmail.com
 */
abstract class View extends Entity {
  
  def select: SelectSequel[T] = new SelectSequel
  
  def select(columns: Column[T]*): SelectSequel[T] = new SelectSequel(columns:_*)
  
}
