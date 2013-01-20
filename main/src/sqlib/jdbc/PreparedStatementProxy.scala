package sqlib.jdbc

import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.sql.PreparedStatement
import java.util.regex.Pattern

private[jdbc] class PreparedStatementProxy private(
    private val stmt: PreparedStatement,
    private val sql: String
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
  
  private val values = new Array[AnyRef]( sql.count( _ == '?' ) )
  
  private[this] def invokeImpl(method: Method, args: Array[AnyRef]): AnyRef = {
    method match {
      case x if x.getName == "setObject" && x.getParameterTypes.size == 2 =>
        val index: Int = args(0).asInstanceOf[Int]
        val value: AnyRef = args(1)
        values(index - 1) = value
      case x if List("executeQuery", "executeUpdate", "execute").contains(x.getName) =>
        println("Connection@%08x %s" format (stmt.getConnection.hashCode(), fmtsql))
      case _ =>
    }
    method.invoke(stmt, args:_*)
  }
  
  private def fmtsql = {
    val buf = new StringBuffer
    
    val matcher = Pattern.compile("""\?""").matcher(sql)
    
    values.foreach { v =>
      if (matcher.find)
        matcher.appendReplacement(buf, v.toString)
    }
    
    matcher.appendTail(buf)
    
    buf.toString
  }
  
}

private[jdbc] object PreparedStatementProxy {
  
  def apply(stmt: PreparedStatement, sql: String) = new PreparedStatementProxy(stmt, sql)
  
}