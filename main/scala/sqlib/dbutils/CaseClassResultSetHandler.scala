package sqlib.dbutils

import java.sql.ResultSet
import org.apache.commons.dbutils.ResultSetHandler
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import sqlib.core.column.Column

/**
 * CaseClassResultSetHandler.
 * 
 * @param <T>
 * 
 * @author fkmt.disk@gmail.com
 */
private[sqlib] class CaseClassResultSetHandler[T] (implicit val tag: ClassTag[T])
    extends ResultSetHandler[List[T]] {
  
  import CaseClassResultSetHandler._
  
  override def handle(rset: ResultSet): List[T] = {
    val klass = tag.runtimeClass.asInstanceOf[Class[T]]
    
    val columns = getColumns(klass)
    
    val meta = rset.getMetaData
    val names = for (i <- 1 to meta.getColumnCount) yield meta.getColumnName(i).toLowerCase
    
    val rows: ListBuffer[T] = ListBuffer()
    
    while (rset.next) {
      val args: Array[AnyRef] = (for (x <- columns) yield null).toArray
      
      names.foreach { name =>
        columns.find(_.name == name) match {
          case None => sys.error(s"column `$name` not found in ${klass.getName}")
          case col => args(col.get.ordinal - 1) = rset.getObject(name)
        }
      }
      
      rows += mkInstance(klass, args)
    }
    
    rows.toList
  }
  
}

private object CaseClassResultSetHandler {
  
  import scala.collection.mutable.WeakHashMap
  import sqlib.core.column.Column
  
  private[this] val cache: WeakHashMap[Class[_], List[Column[_]]] = WeakHashMap()
  
  def getColumns[T](klass: Class[T]): List[Column[T]] = {
    cache.getOrElseUpdate(klass, {
      val instance = getCompanion(klass)
      val companion = instance.getClass
      
      val columns =
        companion.getDeclaredFields.collect {
          case x if classOf[Column[T]].isAssignableFrom(x.getType) => x
        } map { field =>
          field.setAccessible(true)
          field.get(instance).asInstanceOf[Column[T]]
        } sortWith {
          _.ordinal < _.ordinal
        }
      
      columns.toList
    }).asInstanceOf[List[Column[T]]]
  }
  
  private[this] def getCompanion(klass: Class[_]): AnyRef = try {
    Class.forName(s"${klass.getName}$$").getField("MODULE$").get(null)
  } catch {
    case _: ClassNotFoundException | _: NoSuchFieldException =>
      throw new Exception(s"${klass.getName} is not CaseClass")
    case e: Throwable =>
      throw e
  }
  
  private def mkInstance[T](klass: Class[T], args: Array[AnyRef]): T = {
    val instance = getCompanion(klass)
    
    val opt = instance.getClass.getDeclaredMethods.find(_.getName == "apply")
    if (opt.isEmpty)
      throw new Exception(s"${klass.getName} is not CaseClass")
    
    opt.get.invoke(instance, args:_*).asInstanceOf[T]
  }
  
}
