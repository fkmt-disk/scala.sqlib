package sqlib.core

import sqlib.core.column._
import sqlib.core.sequel._

abstract class Entity {
  
  type T
  
  implicit final def bool2column(bool: Boolean): WhereClause[T] = {
    if (bool)
      throw new UnsupportedOperationException("`!=` is unsupported; must be use `<>`")
    WhereClause.get
  }
  
  def select: SelectSequel[T] = new SelectSequel
  
  def select(columns: Column[T]*): SelectSequel[T] = new SelectSequel(columns:_*)
  
  def update: UpdateSequel[T] = new UpdateSequel
  
  def insert: InsertSequel[T] = new InsertSequel
  
  def delete: DeleteSequel[T] = new DeleteSequel
  
}