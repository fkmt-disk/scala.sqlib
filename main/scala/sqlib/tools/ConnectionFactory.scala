package sqlib.tools

/**
 * ConnectionFactory.
 * 
 * @author fkmt.disk@gmail.com
 */
trait ConnectionFactory {
  
  def getConnection: java.sql.Connection
  
}