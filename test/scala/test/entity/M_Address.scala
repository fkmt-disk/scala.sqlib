package test.entity

/**
 * M_Address.
 * 
 * @since 2013-03-16 19:51:10
 */
object M_Address extends sqlib.core.Table {
  
  type T = M_Address.Row
  
  import java.sql.Types._
  import sqlib.core._
  import column._
  
  private[this] val _row_id = new IntColumn[T]("row_id", 1, INTEGER)
  private[this] val _zip_code = new TextColumn[T]("zip_code", 2, CHAR)
  private[this] val _state = new TextColumn[T]("state", 3, VARCHAR)
  private[this] val _city = new TextColumn[T]("city", 4, VARCHAR)
  private[this] val _town = new TextColumn[T]("town", 5, VARCHAR)
  private[this] val _modify_at = new DateColumn[T]("modify_at", 6, TIMESTAMP)
  
  def row_id = _row_id
  def row_id_= (x: java.lang.Integer): SetClause[T] = set(_row_id, x)
  def zip_code = _zip_code
  def zip_code_= (x: java.lang.String): SetClause[T] = set(_zip_code, x)
  def state = _state
  def state_= (x: java.lang.String): SetClause[T] = set(_state, x)
  def city = _city
  def city_= (x: java.lang.String): SetClause[T] = set(_city, x)
  def town = _town
  def town_= (x: java.lang.String): SetClause[T] = set(_town, x)
  def modify_at = _modify_at
  def modify_at_= (x: java.util.Date): SetClause[T] = set(_modify_at, x)
  
  @EntityInfo(name="m_address")
  case class Row(
    row_id: java.lang.Integer,
    zip_code: java.lang.String,
    state: java.lang.String,
    city: java.lang.String,
    town: java.lang.String,
    modify_at: java.util.Date
  )
  
}
