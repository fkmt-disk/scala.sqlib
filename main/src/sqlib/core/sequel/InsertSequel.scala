package sqlib.core.sequel

import java.sql.Connection
import scala.collection.mutable.ListBuffer
import sqlib.core._

final class InsertSequel[T] private[core] {
  
  private[this] var _values: List[SetClause[T]] = Nil
  
  def values(values: SetClause[T]*): InsertSequel[T] = {
    _values = values.toList
    this
  }
  
  def go(conn: Connection)(implicit manifest: ClassManifest[T]): Int = {
    if (_values.isEmpty)
      throw new IllegalStateException("values is empty")
    
    val klass: Class[_] = manifest.erasure
    
    val buff = new ListBuffer[String]
    
    buff append "insert into"
    
    buff append klass.getSimpleName.toLowerCase
    
    buff append ("(" +  _values.map(_.name).mkString(", ") + ")")
    
    buff append "values"
    
    buff append ("(" +  ("?" * _values.length).toCharArray.mkString(", ") + ")")
    
    val sql = buff.mkString(" ")
    
    printf("%s; %s%n", sql, _values.map(_.value))
     
    // TODO
    -1
  }
  
}