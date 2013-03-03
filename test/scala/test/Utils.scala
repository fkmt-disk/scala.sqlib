package test

object Utils {
  
  def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    import scala.language.reflectiveCalls
    try {
      main(resource)
    }
    finally {
      resource.close()
    }
  }
  
  import scala.language.implicitConversions
  
  implicit class Rows[T](list: List[T]) {
    def each(f: T => Unit): Rows[T] = {
      list.foreach(f)
      this
    }
    def whenEmpty(f: => Unit) = {
      if (list.isEmpty)
        f
    }
  }
  
}