package test

import test.jdbc.DerbyDataSource
import test.entity.M_Address
import test.Utils._
import java.util.Date

object Main extends App {
  
  using(DerbyDataSource.getConnection) { conn =>
    println(conn.getMetaData.getDatabaseProductName)
    
    import M_Address._
    
    println("--------------------------------------------------")
    
    val del = M_Address.delete.go(conn)
    
    println(s"deleted $del records")
    
    println("--------------------------------------------------")
    
    val ins = M_Address.insert.values(
        row_id = 1,
        zip_code = "0030002",
        state="hokkaido",
        city="sapporo city",
        town="higashi-sapporo"
    ).go(conn)
    
    println(s"inserted $ins records")
    
    println("--------------------------------------------------")
    
    M_Address
      .select.go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    M_Address
      .select(zip_code).go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    M_Address
      .select
      .where(zip_code ~ "300")
      .go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    M_Address
      .select
      .where(zip_code ~ "300" and row_id <> 1)
      .go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    M_Address
      .select
      .where(modify_at < new Date)
      .go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    M_Address
      .select
      .where(modify_at >= new Date)
      .go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    M_Address
      .select(state, city, town)
      .orderBy(row_id.asc)
      .go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
    
    val upd = M_Address
      .update.set(city = "SapporoCity", modify_at = new Date)
      .where(city == "sapporo city")
      .go(conn)
    
    println(s"updated $ins records")
    
    println("--------------------------------------------------")
    
    M_Address
      .select.go(conn)
      .each(println)
      .whenEmpty(println("no record"))
    
    println("--------------------------------------------------")
  }
  
}