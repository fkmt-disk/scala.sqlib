package entity

import java.util.Date

import sqlib.core._

case class FooTable(
    pkey: Number,
    varchae_column: String,
    entry_date: Date
) extends Table {

}

object FooTable extends Table {
  
  type T = FooTable
  
  val pkey = new NumberColumn[FooTable]("pkey")
  
  val varchar_column = new TextColumn[FooTable]("varchar_column")
  
  val entry_date = new DateColumn[FooTable]("entry_date")



}