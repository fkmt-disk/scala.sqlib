package sqlib.core

/**
 * AsAnyRef.
 * 
 * @author fkmt.disk@gmail.com
 */
private[core] trait AsAnyRef {
  
  import scala.language.implicitConversions
  
  implicit protected class AsAnyRef(x: Any) {
    def asAnyRef = x.asInstanceOf[AnyRef]
  }
  
}
