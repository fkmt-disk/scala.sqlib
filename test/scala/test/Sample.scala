package test

import java.util.Date
import test.jdbc.DerbyConnectionFactory
import test.entity.M_Address
import test.Utils._

object Sample extends App {
  
  using(DerbyConnectionFactory.getConnection) { conn =>
    println(conn.getMetaData.getDatabaseProductName)
    
    import M_Address._
    
    val rows: List[M_Address] = M_Address
      .select( row_id, zip_code, modify_at )
      .where( row_id >= 123 and (state ~ "hoge" or town <> "fuga") )
      .distinct
      .orderBy( modify_at.desc, zip_code.asc )
      .go(conn)
    // => select distinct row_id, zip_code, modify_at
    //    from m_address
    //    where ( row_id >= ? and ( state like ('%' || ? || '%') or town <> ? ) )
    //    order by modify_at desc, zip_code asc
    // List(123, hoge, fuga)
    
    M_Address.select.where( row_id == None and state ~ "hoge" or town <> "fuga" ).go(conn)
    // => select * from m_address
    //    where ( row_id is null and state like ('%' || ? || '%') or town <> ? )
    // List(None, hoge, fuga)
    
    M_Address.select.go(conn)
    // => select * from m_address
    
    M_Address.select(row_id).distinct.go(conn)
    // => select distinct row_id from m_address
    
    M_Address.select.orderBy( row_id.asc ).go(conn)
    // => select * from m_address order by row_id asc
    
    val mod_row_count = M_Address.update
      .set( row_id = null, modify_at = "2013-02-27 00:22:12" )
      .where( state == "piyo" )
      .go(conn)
    // => update m_address
    //    set row_id = ?, modify_at = ?
    //    where ( state = ? )
    // List(null, Wed Feb 27 00:22:12 JST 2013, piyo)
    
    M_Address.update.set( row_id = null, modify_at = new Date ).go(conn)
    // => update m_address set row_id = ?, modify_at = ?
    // List(null, Wed Feb 27 00:52:39 JST 2013)
    
    M_Address.update.where( state == "piyo" ).go(conn)
    // => update m_address where ( state = ? )
    // List(piyo)
    
    M_Address.insert.values( row_id = 123, state = "hoge", city = "fuga" ).go(conn)
    // => insert into m_address (row_id, state, city) values (?, ?, ?)
    // List(123, hoge, fuga)
    
    M_Address.delete.where( row_id > 100 ).go(conn)
    // => delete from m_address where ( row_id > ? )
    // List(100)
    
    M_Address.delete.go(conn)
    // => delete from m_address
    
  }
  
}
