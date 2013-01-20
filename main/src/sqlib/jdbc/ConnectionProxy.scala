package sqlib.jdbc

import java.lang.reflect.{ InvocationTargetException, Method, InvocationHandler, Proxy }
import java.sql.{ Connection, PreparedStatement }

private[jdbc] class ConnectionProxy private (
    private val conn: Connection
) extends InvocationHandler {
  
  override def invoke(proxy: AnyRef, method: Method, args: Array[AnyRef]): AnyRef = {
    try {
      invokeImpl(method, args)
    }
    catch {
      case e: InvocationTargetException => throw e.getTargetException
      case e => throw e
    }
  }
  
  private[this] def invokeImpl(method: Method, args: Array[AnyRef]): AnyRef = {
    var ret = method.invoke(conn, args: _*)
    
    method match {
      case x if x.getName == "prepareStatement" && x.getParameterTypes.size == 1 =>
        ret = PreparedStatementProxy(ret.asInstanceOf[PreparedStatement], args(0).asInstanceOf[String])
      case x if List("close", "commit", "rollback").contains(x.getName) =>
        println("Connection@%08x %s" format (conn.hashCode, x.getName))
      case x if x.getName == "setAutoCommit" =>
        println("Connection@%08x %s %s" format (conn.hashCode, x.getName, args(0)))
      case _ =>
    }
    
    ret
  }
  
}

private[jdbc] object ConnectionProxy {
  
  def apply(conn: Connection): Connection = {
    val clsldr = classOf[Connection].getClassLoader
    val ifaces: Array[Class[_]] = Array(classOf[Connection])
    val proxy = new ConnectionProxy(conn)
    Proxy.newProxyInstance(clsldr, ifaces, proxy).asInstanceOf[Connection]
  }
  
}