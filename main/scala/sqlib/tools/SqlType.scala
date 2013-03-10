package sqlib.tools

/**
 * SqlType.
 * 
 * @author fkmt.disk@gmail.com
 */
private[tools] class SqlType(sqlType: Int) {
  
  import java.sql.Types._
  import scala.reflect.BeanProperty
  import sqlib.core.column._
  
  @BeanProperty
  val typeName: String = sqlType match {
    case t if t == CHAR         => "CHAR"
    case t if t == VARCHAR      => "VARCHAR"
    case t if t == LONGVARCHAR  => "LONGVARCHAR"
    case t if t == BIT          => "BIT"
    case t if t == BOOLEAN      => "BOOLEAN"
    case t if t == TINYINT      => "TINYINT"
    case t if t == SMALLINT     => "SMALLINT"
    case t if t == INTEGER      => "INTEGER"
    case t if t == BIGINT       => "BIGINT"
    case t if t == REAL         => "REAL"
    case t if t == FLOAT        => "FLOAT"
    case t if t == DOUBLE       => "DOUBLE"
    case t if t == DECIMAL      => "DECIMAL"
    case t if t == NUMERIC      => "NUMERIC"
    case t if t == DATE         => "DATE"
    case t if t == TIME         => "TIME"
    case t if t == TIMESTAMP    => "TIMESTAMP"
  }
  
  @BeanProperty
  val javaType: Class[_] = sqlType match {
    case t if List(CHAR, VARCHAR, LONGVARCHAR).contains(t)  => classOf[String]
    case t if List(BIT, BOOLEAN).contains(t)                => classOf[Boolean]
    case t if List(TINYINT, SMALLINT, INTEGER).contains(t)  => classOf[Integer]
    case t if List(BIGINT).contains(t)                      => classOf[Long]
    case t if List(REAL, FLOAT, DOUBLE).contains(t)         => classOf[Double]
    case t if List(DECIMAL, NUMERIC).contains(t)            => classOf[BigDecimal]
    case t if List(DATE, TIME, TIMESTAMP).contains(t)       => classOf[java.util.Date]
    case x =>
      sys.error(s"unsupported sql types: $x")
  }
  
  @BeanProperty
  val columnType: Class[_] = sqlType match {
    case t if List(CHAR, VARCHAR, LONGVARCHAR).contains(t)  => classOf[TextColumn[_]]
    case t if List(BIT, BOOLEAN).contains(t)                => classOf[BoolColumn[_]]
    case t if List(TINYINT, SMALLINT, INTEGER).contains(t)  => classOf[IntColumn[_]]
    case t if List(BIGINT).contains(t)                      => classOf[LongColumn[_]]
    case t if List(REAL, FLOAT, DOUBLE).contains(t)         => classOf[DoubleColumn[_]]
    case t if List(DECIMAL, NUMERIC).contains(t)            => classOf[DecimalColumn[_]]
    case t if List(DATE, TIME, TIMESTAMP).contains(t)       => classOf[DateColumn[_]]
    case x =>
      sys.error(s"unsupported sql types: $x")
  }
  
}
