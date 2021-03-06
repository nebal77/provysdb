package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for String class.
 */
@Immutable
public class SqlTypeAdapterString implements SqlTypeAdapter<String> {

  private static final SqlTypeAdapterString INSTANCE = new SqlTypeAdapterString();
  private static final long serialVersionUID = -6054974313349658918L;

  /**
   * Instance of String type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterString getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterString() {
  }

  @Override
  public Class<String> getType() {
    return String.class;
  }

  @Override
  public int getSqlType() {
    return Types.VARCHAR;
  }

  @Override
  public String readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullString(columnIndex);
  }

  @Override
  public String readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullString(columnLabel);
  }

  @Override
  public @Nullable String readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableString(columnIndex);
  }

  @Override
  public @Nullable String readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableString(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable String value) {
    statement.setNullableString(parameterIndex, value);
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterString{}";
  }
}
