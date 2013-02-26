package sqlib.core

import sqlib.core._
import sqlib.core.column._
import sqlib.core.sequel._

abstract class Entity {
  
  type T
  
  def select: SelectSequel[T] = new SelectSequel
  
  def select(columns: Column[T]*): SelectSequel[T] = new SelectSequel(columns:_*)
  
  def update: UpdateSequel[T] = new UpdateSequel
  
  def insert: InsertSequel[T] = new InsertSequel
  
  def delete: DeleteSequel[T] = new DeleteSequel
  
  protected def set(column: Column[T], x: Any): SetClause[T] = SetClause(column.name, x)
  
  implicit final def bool2column(x: Boolean): WhereClause[T] = Preamble.bool2column(x)
  
  implicit final def str2date(x: String): java.util.Date = Preamble.str2date(x)
  
  implicit final def int2opt(x: Int): Option[Int] = Preamble.int2opt(x)
  
}
