package com.provys.provysdb.sqldefault.expression;

import com.provys.provysdb.sql.CodeBuilder;

/**
 * Common ancestor for number literals.
 *
 * @param <T> is Java type whose value is used in this literal.
 */
abstract class LiteralNumber<T extends Number> extends LiteralBase<T> {

  LiteralNumber(T value) {
    super(value);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append(getValue().toString());
  }
}