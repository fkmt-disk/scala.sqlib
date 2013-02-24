package test

import java.util.Date
import jdbc.DerbyDataSource
import entity.M_Address
import entity.M_Address._

object Main extends App {
  
  def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    try { main(resource) }
    finally { resource.close() }
  }
  
  printf("%s%n", new java.io.File(".").getCanonicalPath)
  
  using(DerbyDataSource.getConnection) { conn =>
    println(conn.getMetaData.getDatabaseProductName)
    
    val rows: List[M_Address] = M_Address
      .select( row_id, zip_code, modify_at )
      .where( row_id >= 123 and (state ~ "hoge" or town <> "fuga") )
      .distinct
      .orderBy( modify_at.desc, zip_code.asc )
      .go(conn)
    
    M_Address.select.where( row_id >= 123 and state ~ "hoge" or town <> "fuga" ).go(conn)
    
    M_Address.select.go(conn)
    
    M_Address.select(row_id).distinct.go(conn)
    
    M_Address.select.orderBy( row_id.asc ).go(conn)
    
    M_Address.update.set( row_id := None, modify_at := new Date ).where( state == "piyo" ).go(conn)
    
    M_Address.update.set( row_id := None, modify_at := new Date ).go(conn)
    
    M_Address.update.where( state == "piyo" ).go(conn)
    
    M_Address.insert.values( row_id := 123, state := "hoge", city := "fuga" ).go(conn)
    
    M_Address.delete.where( row_id > 100 ).go(conn)
    
    M_Address.delete.go(conn)
    
  }
  
}
