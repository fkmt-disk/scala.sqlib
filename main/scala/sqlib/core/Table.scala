package sqlib.core

import sqlib.core.sequel.DeleteSequel
import sqlib.core.sequel.InsertSequel
import sqlib.core.sequel.UpdateSequel

/**
 * Table.
 * 
 * @author fkmt.disk@gmail.com
 */
abstract class Table extends View {
  
  def update: UpdateSequel[T] = new UpdateSequel
  
  def insert: InsertSequel[T] = new InsertSequel
  
  def delete: DeleteSequel[T] = new DeleteSequel
  
}
