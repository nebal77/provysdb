package com.provys.db.sqldb.sql;

import com.provys.db.sqldb.sql.codebuilder.CodeBuilderImpl;
import com.provys.db.sql.CodeBuilder;

/**
 * Static factory class.
 */
public final class CodeBuilderFactory {

  /**
   * Create new CodeBuilder instance.
   *
   * @return new CodeBuilder instance
   */
  public static CodeBuilder getCodeBuilder() {
    return new CodeBuilderImpl();
  }

  /**
   * Static factory class.
   */
  private CodeBuilderFactory() {

  }
}
