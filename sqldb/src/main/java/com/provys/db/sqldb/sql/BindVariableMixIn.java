package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.db.sql.BindName;
import com.provys.db.sqldb.dbcontext.DefaultJsonClassSerializer;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Jackson mix-in for {@link com.provys.db.sql.BindVariable} class. Overrides default BindVariable
 * serialization / deserialization with one where class is translated to name based on SQL type map.
 */
@SuppressWarnings({"FieldCanBeLocal", "ClassHasNoToStringMethod", "UnusedVariable"})
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("BINDVARIABLE")
@JsonDeserialize(using = BindVariableDeserializer.class)
public abstract class BindVariableMixIn {

  @JsonProperty("NAME")
  private final BindName name;
  @JsonProperty("TYPE")
  @JsonSerialize(using = DefaultJsonClassSerializer.class)
  private final Class<?> type;
  @JsonProperty("VALUE")
  private final @Nullable Object value;

  /**
   * Create new bind variable mock.
   *
   * @param name is name of new bind variable
   * @param type is type of new bind variable
   * @param value is default value for bind variable
   */
  public BindVariableMixIn(BindName name, Class<?> type, @Nullable Object value) {
    this.name = name;
    this.type = type;
    this.value = value;
  }
}
