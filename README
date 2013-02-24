create table m_address(
    row_id integer not null
  , zip_code char(7) not null
  , state varchar(128) not null
  , city varchar(128) not null
  , town varchar(128) not null
  , modify_at timestamp not null default current timestamp
  , constraint m_address_pkc primary key (row_id)
)

こんなテーブルがあった時に、

val rows: List[M_Address] = M_Address.select.where( row_id >= 123 and town <> "fuga" ).go(conn)

こんなふうにSELECTを書きたいSCALAのSQLライブラリ。


まだ未完成。
SQL文を構築するロジックだけ作った。
実際にDBにSQL投げるところはこれから。



val rows: List[M_Address] = M_Address
  .select( row_id, zip_code, modify_at )
  .where( row_id >= 123 and (state ~ "hoge" or town <> "fuga") )
  .distinct
  .orderBy( modify_at.desc, zip_code.asc )
  .go(conn)

このコードは

select distinct row_id, zip_code, modify_at
from m_address
where ( row_id >= 123 and ( state like '%' || ? || '%' or town <> ?) )
order by modift_at desc, zip_code asc

[123, hoge, fuga]

こんなSQLを作る。
PreparedStatementでDBに投げる（予定）。

※） 「~」はlike
※） 「!=」は使えない「<>」のみ


その他、サンプル

M_Address.select.go(conn)

M_Address.select(row_id).distinct.go(conn)

M_Address.select.orderBy( row_id.asc ).go(conn)

M_Address.update.set( row_id := None, modify_at := new Date ).where( state == "piyo" ).go(conn)

M_Address.update.set( row_id := None, modify_at := new Date ).go(conn)

M_Address.update.where( state == "piyo" ).go(conn)

M_Address.insert.values( row_id := 123, state := "hoge", city := "fuga" ).go(conn)

M_Address.delete.where( row_id > 100 ).go(conn)

M_Address.delete.go(conn)



■TODO
・作ったSQL文をDBに投げるところ作り込む
・各テーブルに対応するEntityクラスをDataBaseMetaDataから読み取って自動生成する


