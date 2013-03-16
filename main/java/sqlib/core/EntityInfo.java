package sqlib.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * EntityInfo.
 * 
 * @author fkmt.disk@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityInfo {
  
  /**
   * テーブル名
   */
  String name();
  
}
