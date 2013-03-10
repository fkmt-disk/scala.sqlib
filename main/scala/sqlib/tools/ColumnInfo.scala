package sqlib.tools

import scala.reflect.BeanProperty

/**
 * ColumnInfo.
 * 
 * @author fkmt.disk@gmail.com
 */
private[tools] class ColumnInfo {
  
  // テーブルカタログ (nullの可能性がある)
  @BeanProperty var tableCat: String = _
  
  // テーブルスキーマ (nullの可能性がある)
  @BeanProperty var tableSchem: String = _
  
  // テーブル名
  @BeanProperty var tableName: String = _
  
  // 列名
  @BeanProperty var columnName: String = _
  
  // java.sql.TypesからのSQLの型
  @BeanProperty var dataType: Int = _
  
  // データソース依存の型名。 UDTの場合、型名は完全指定
  @BeanProperty var typeName: String = _
  
  // 列サイズ
  @BeanProperty var columnSize: Int = _
  
  // 小数点以下の桁数。 DECIMAL_DIGITSが適用できないデータ型の場合は、Nullが返される。
  @BeanProperty var decimalDigits: Int = _
  
  // 基数 (通常は、10または2のどちらか)
  @BeanProperty var numPrecRadix: Int = _
  
  // 0:NULL値を許さない可能性がある / 1:必ずNULL値を許す / 2:NULL値を許すかどうかは不明
  @BeanProperty var nullable: Int = _
  
  // コメント記述列 (nullの可能性がある)
  @BeanProperty var remarks: String = _
  
  // 列のデフォルト値。 単一引用符で囲まれた値は、文字列として解釈されなければならない (nullの可能性がある)
  @BeanProperty var columnDed: String = _
  
  // charの型については列の最大バイト数
  @BeanProperty var charOctetLength: Int = _
  
  // テーブル中の列のインデックス (1から始まる)
  @BeanProperty var ordinalPosition: Int = _
  
  // YES:パラメータがNULLを許可する / NO:パラメータがNULLを許可しない / 空の文字列:パラメータがNULL値を許可するかどうか不明
  @BeanProperty var isNullable: String = _
  
  // 参照属性のスコープであるテーブルのカタログ (DATA_TYPEがREFでない場合はnull)
  @BeanProperty var scopeCatlog: String = _
  
  // 参照属性のスコープであるテーブルのスキーマ (DATA_TYPEがREFでない場合はnull)
  @BeanProperty var scopeSchema: String = _
  
  // 参照属性のスコープであるテーブル名 (DATA_TYPEがREFでない場合はnull)
  @BeanProperty var scopeTable: String = _
  
  // 個別の型またはユーザー生成 Ref 型、java.sql.Types の SQL 型のソースの型 (DATA_TYPE が DISTINCT またはユーザー生成 REF でない場合は null)
  @BeanProperty var sourceDataType: Short = _
  
  // YES:列が自動インクリメントされる / NO:列が自動インクリメントされない / 空の文字列:列が自動インクリメントされるかどうかが判断できない
  @BeanProperty var isAutoincrement: String = _
  
  @BeanProperty var sqlType: SqlType = null
  
}

private[tools] object ColumnInfo {
  
  val map = Map(
    "TABLE_CAT"         -> "tableCat",
    "TABLE_SCHEM"       -> "tableSchem",
    "TABLE_NAME"        -> "tableName",
    "COLUMN_NAME"       -> "columnName",
    "DATA_TYPE"         -> "dataType",
    "TYPE_NAME"         -> "typeName",
    "COLUMN_SIZE"       -> "columnSize",
    "DECIMAL_DIGITS"    -> "decimapDigits",
    "NUM_PREC_RADIX"    -> "numPrecRadix",
    "NULLABLE"          -> "nullable",
    "REMARKS"           -> "remarks",
    "COLUMN_DEF"        -> "columnDef",
    "CHAR_OCTET_LENGTH" -> "charOctetLength",
    "ORDINAL_POSITION"  -> "ordinalPosition",
    "IS_NULLABLE"       -> "isNullable",
    "SCOPE_CATLOG"      -> "scopeCatlog",
    "SCOPE_SCHEMA"      -> "scopeSchema",
    "SCOPE_TABLE"       -> "scopeTable",
    "SOURCE_DATA_TYPE"  -> "sourceDataType",
    "IS_AUTOINCREMENT"  -> "isAutoincrement"
  )
  
}
