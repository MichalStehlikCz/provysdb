package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Condition based on built-in conditional operator.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("CONDOP")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
@Immutable
final class ConditionOperation implements Condition {

  @JsonProperty("OPERATOR")
  private final ConditionalOperator operator;
  @JsonProperty("ARGUMENTS")
  @JacksonXmlProperty(localName = "ARGUMENT")
  @JacksonXmlElementWrapper(localName = "ARGUMENTS", useWrapping = true)
  @SuppressWarnings("Immutable") // list product of copyOf, Expression is immutable
  private final List<Expression<?>> arguments;

  /**
   * Create function that will evaluate to supplied type, based on supplied function and using
   * arguments. Type of function and arguments are validated against function definition.
   *
   * @param operator is conditional operator, used for evaluation
   * @param arguments are arguments to be passed to operator
   */
  @JsonCreator
  ConditionOperation(@JsonProperty("OPERATOR") ConditionalOperator operator,
      @JsonProperty("ARGUMENTS") Collection<? extends Expression<?>> arguments) {
    var argumentTypes = arguments.stream().map(Expression::getType).collect(Collectors.toList());
    operator.validateArguments(argumentTypes);
    this.operator = operator;
    this.arguments = List.copyOf(arguments);
  }

  /**
   * Value of field operator.
   *
   * @return value of field operator
   */
  public ConditionalOperator getOperator() {
    return operator;
  }

  /**
   * Value of field arguments.
   *
   * @return value of field arguments
   */
  public List<Expression<?>> getArguments() {
    return arguments;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    BindVariableCollector binds = new BindVariableCollector();
    for (var argument : arguments) {
      binds.add(argument);
    }
    return binds.getBinds();
  }

  @Override
  public Condition mapBinds(BindMap bindMap) {
    var newArguments = arguments.stream()
        .map(argument -> argument.mapBinds(bindMap))
        .collect(Collectors.toList());
    if (arguments.equals(newArguments)) {
      return this;
    }
    return new ConditionOperation(operator, newArguments);
  }

  @Override
  public void apply(ConditionConsumer consumer) {
    consumer.condition(operator, arguments);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionOperation that = (ConditionOperation) o;
    return operator == that.operator
        && arguments.equals(that.arguments);
  }

  @Override
  public int hashCode() {
    int result = operator.hashCode();
    result = 31 * result + arguments.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ConditionOperation{"
        + "operator=" + operator
        + ", arguments=" + arguments
        + '}';
  }
}