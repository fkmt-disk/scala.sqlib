package entity

import java.util.Date

import sqlib.core._
import sqlib.core.column._

case class M_Address(
    row_id: Int,
    zip_code: String,
    state: String,
    city: String,
    town: String,
    modift_at: Date
)

object M_Address extends Entity {
  
  type T = M_Address
  
  import java.sql.Types._
  
  val row_id = new IntColumn[T]("row_id", INTEGER)
  
  val zip_code = new TextColumn[T]("zip_code", CHAR)
  
  val state = new TextColumn[T]("state", VARCHAR)
  
  val city = new TextColumn[T]("city", VARCHAR)
  
  val town = new TextColumn[T]("town", VARCHAR)
  
  val modify_at = new DateColumn[T]("modify_at", TIMESTAMP)
  
}