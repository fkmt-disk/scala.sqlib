package sqlib.tools

import scala.reflect.BeanProperty

/**
 * TableInfo.
 * 
 * @author fkmt.disk@gmail.com
 */
private[tools] class TableInfo {
  
  // テーブルカタログ (nullの可能性がある)
  @BeanProperty var tableCat: String = _
  
  // テーブルスキーマ (nullの可能性がある)
  @BeanProperty var tableSchem: String = _
  
  // テーブル名
  @BeanProperty var tableName: String = _
  
  // テーブルの型
  @BeanProperty var tableType: String = _
  
  // テーブルに関する説明
  @BeanProperty var remarks: String = _
  
  // 型のカタログ (nullの可能性がある)
  @BeanProperty var typeCat: String = _
  
  // 型のスキーマ (nullの可能性がある)
  @BeanProperty var typeSchem: String = _
  
  // 型名 (nullの可能性がある)
  @BeanProperty var typeName: String = _
  
  // 型付きテーブルの指定された「識別子」列の名前 (nullの可能性がある)
  @BeanProperty var selfReferencingColName: String = _
  
  // SELF_REFERENCING_COL_NAMEの値の作成方法を指定する。
  @BeanProperty var refGeneration: String = _
  
  @BeanProperty var columns: java.util.List[ColumnInfo] = _
  
  @BeanProperty var tableClassName: String = _
  
  @BeanProperty var superType: Class[_] = _
  
}

private[tools] object TableInfo {
  val map = {
    val map = scala.collection.mutable.Map[String, String]()
    Map(
      "TABLE_CAT"                 -> "tableCat",
      "TABLE_SCHEM"               -> "tableSchem",
      "TABLE_NAME"                -> "tableName",
      "TABLE_TYPE"                -> "tableType",
      "REMARKS"                   -> "remarks",
      "TYPE_CAT"                  -> "typeCat",
      "TYPE_SCHEM"                -> "typeSchem",
      "TYPE_NAME"                 -> "typeName",
      "SELF_REFERENCING_COL_NAME" -> "selfReferencingColName",
      "REF_GENERATION"            -> "refGeneration"
    ).iterator.foreach { entry =>
      map += entry._1 -> entry._2
      map += entry._1.toLowerCase -> entry._2
    }
    map.toMap
  }
  
}
