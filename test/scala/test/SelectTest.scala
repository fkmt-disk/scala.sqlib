package test

import jdbc.DerbyDataSource
import entity.M_Address

object SelectTest extends App {
  
  def using[A <: {def close(): Unit}, B](resource: A)(main: A => B): B = {
    try { main(resource) } finally { resource.close() }
  }
  
  using(DerbyDataSource.getConnection) { conn =>
    println(conn.getMetaData.getDatabaseProductName)
    
    import M_Address._
    
    println("--------------------------------------------------")
    
    M_Address.select.go(conn).foreach(println)
    
    println("--------------------------------------------------")
    
    M_Address.select(state,city).go(conn).foreach(println)
    
    println("--------------------------------------------------")
    
    M_Address.select.where(row_id == 1).go(conn).foreach(println)
    
    println("--------------------------------------------------")
    
    M_Address.select.where(row_id <> 1).go(conn).foreach(println)
    
    println("--------------------------------------------------")
    
    M_Address.select.where(state ~ "kk").orderBy(modify_at.desc).go(conn).foreach(println)
    
    println("--------------------------------------------------")
    
    M_Address.select.where(state ~ "hoge").orderBy(modify_at.desc).go(conn).foreach(println)
    
    println("--------------------------------------------------")
  }
  
}