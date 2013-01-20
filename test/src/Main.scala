package test

import sqlib.Utils.using
import sqlib.jdbc.DerbyDataSource
import entity.FooTable
import entity.FooTable._

object Main extends App {
  
  printf("%s%n", new java.io.File(".").getCanonicalPath)
  
  using(DerbyDataSource.getConnection) { conn =>
    println(conn.getMetaData.getDatabaseProductName)

    FooTable.select( pkey == 123 )

  }
  
}
