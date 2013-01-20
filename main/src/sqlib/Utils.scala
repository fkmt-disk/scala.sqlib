package sqlib

object Utils {
  
  def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    try {
      main(resource)
    }
    finally {
      resource.close()
    }
  }
  
}
