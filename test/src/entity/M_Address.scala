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
  
  private[this] val _row_id = new IntColumn[T]("row_id", INTEGER)
  
  private[this] val _zip_code = new TextColumn[T]("zip_code", CHAR)
  
  private[this] val _state = new TextColumn[T]("state", VARCHAR)
  
  private[this] val _city = new TextColumn[T]("city", VARCHAR)
  
  private[this] val _town = new TextColumn[T]("town", VARCHAR)
  
  private[this] val _modify_at = new DateColumn[T]("modify_at", TIMESTAMP)
  
  def row_id = _row_id
  
  def row_id_= (x: Option[Int]): SetClause[T] = set(_row_id, x.getOrElse(null))
  
  def zip_code = _zip_code
  
  def zip_code_= (x: String): SetClause[T] = set(_zip_code, x)
  
  def state = _state
  
  def state_= (x: String): SetClause[T] = set(_state, x)
  
  def city = _city
  
  def city_= (x: String): SetClause[T] = set(_city, x)
  
  def town = _town
  
  def town_= (x: String): SetClause[T] = set(_town, x)
  
  def modify_at = _modify_at
  
  def modify_at_= (x: Date): SetClause[T] = set(_modify_at, x)
  
}