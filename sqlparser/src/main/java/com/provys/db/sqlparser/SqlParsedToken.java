package com.provys.db.sqlparser;

import com.google.errorprone.annotations.Immutable;

/**
 * Represents token parsed from Sql source, usually retrieved via tokenizer. Extends token, adds
 * information about position in original source
 */
@Immutable
public interface SqlParsedToken extends SqlToken {

  /**
   * Line token was parsed from.
   *
   * @return line of source token was parsed from
   */
  int getLine();

  /**
   * Start position of token in original source.
   *
   * @return position in line where token starts
   */
  int getPos();
}
