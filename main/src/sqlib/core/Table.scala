package sqlib.core

abstract class Table {
  
  type T <: Table

  final def select(where: => WhereClause[T])(implicit c: ClassManifest[T]): List[T] = {
    WhereClause.clear

    val clause = where
    val sql = clause.mksql

    printf("sql=%s%n", sql._1)
    printf("params=%s%n", sql._2)

    val klass = c.erasure
    /*
    val constructor = t.erasure.getConstructor(Array[Class](classOf[ResultSet]))
    val table = constructor.newInstance
    */
    println(klass)

    List()
  }

  implicit final def bool2column(bool: Boolean): WhereClause[T] = {
    if (bool)
      throw new UnsupportedOperationException("`!=` is unsupported; must be use `<>`")
    WhereClause.get
  }

}
