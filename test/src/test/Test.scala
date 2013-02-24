package test

import jdbc.DerbyDataSource

object Test extends App {
  def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    try { main(resource) }
    finally { resource.close() }
  }
  
  printf("%s%n", new java.io.File(".").getCanonicalPath)
  
  using(DerbyDataSource.getConnection) { conn =>
    using(conn.createStatement) { stmt =>
      stmt.execute(
          """|create table m_address(
             |    row_id integer not null
             |  , zip_code char(7) not null
             |  , state varchar(128) not null
             |  , city varchar(128) not null
             |  , town varchar(128) not null
             |  , modify_at timestamp not null default CURRENT
             |  , constraint m_address_pkc primay key (row_id)
             |);""".stripMargin
      )
    }
  }

}