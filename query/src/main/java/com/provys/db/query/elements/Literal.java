package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("LITERAL")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
@JsonSerialize(using = LiteralSerializer.class)
@JsonDeserialize(using = LiteralDeserializer.class)
final class Literal<T> implements Expression<T> {

  private final Class<T> type;
  private final @Nullable T value;

  Literal(Class<T> type, @Nullable T value) {
    this.type = type;
    this.value = value;
  }

  @SuppressWarnings("unchecked") // not exactly correct (for generic types), but close enough...
  private static <T> Class<T> getType(@NonNull T value) {
    return (Class<T>) value.getClass();
  }

  Literal(@NonNull T value) {
    this(getType(value), value);
  }

  public @Nullable T getValue() {
    return value;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public Expression<T> mapBinds(BindMap bindMap) {
    return this;
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.literal(type, value);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Literal<?> literal = (Literal<?>) o;
    return type == literal.type
        && Objects.equals(value, literal.value);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Literal{"
        + "type=" + type
        + ", value=" + value
        + '}';
  }
}
