package com.provys.db.sql;

import com.provys.db.dbcontext.DbContext;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Context provides database access (via getConnection methods), factory to create elements and
 * language context for target environment. It gives access to type map, that can be used to express
 * literals. It can also hold settings that affect generation of texts.
 */
@SuppressWarnings("CyclicClassDependency")
public interface Context<S extends Select, A extends SelectClause, C extends SelectColumn,
    F extends FromClause, J extends FromElement, W extends Condition, E extends Expression>
    extends DbContext {

  /**
   * Create Sql name object based on supplied text. Validates name during creation.
   *
   * @param text is string, representing new identifier
   * @return identifier corresponding to supplied text
   */
  default SimpleName name(String text) {
    return SimpleName.valueOf(text);
  }

  /**
   * Create path (typically scheme.object or scheme.object.component) from supplied identifiers
   *
   * @param segments is list of segments path consists of
   * @return path consisting of supplied segments
   */
  default NamePath path(Collection<SimpleName> segments) {
    return SegmentedName.ofSegments(segments);
  }

  /**
   * Create path (typically scheme.object or scheme.object.component) from supplied text
   *
   * @param text is text with segments, delimited by .
   * @return path corresponding to supplied text
   */
  default NamePath path(String text) {
    return SegmentedName.valueOf(text);
  }

  /**
   * Create bind name based on supplied String.
   *
   * @param name is name of bind value, case insensitive
   * @return bind name
   */
  default BindName bind(String name) {
    return BindName.ofValue(name);
  }

  /**
   * Get unknown from context, that allows any source table, but does not contain any from elements.
   * Context is used for selects, embedded in expression or condition that are created without
   * context.
   *
   * @return default unknown context
   */
  default FromContext getUnknownFromContext() {
    return UnknownFromContext.getInstance();
  }

  /**
   * Create select statement with specified select clause and from clause.
   *
   * @param selectClause defines projection of from clause to results
   * @param fromClause   defines data sources
   * @param bindMap      if specified, replace bind variables with ones found in this map
   * @return select based on supplied clauses
   */
  default S select(SelectClause selectClause, FromClause fromClause, @Nullable BindMap bindMap) {
    return select(selectClause, fromClause, null, bindMap);
  }

  /**
   * Create select statement with specified select clause, from clause and where clause.
   *
   * @param selectClause defines projection of from clause to results
   * @param fromClause   defines data sources
   * @param whereClause  defines filtering criteria
   * @param bindMap      if specified, replace bind variables with ones found in this map
   * @return select based on supplied clauses
   */
  default S select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable BindMap bindMap) {
    return select(selectClause, fromClause, whereClause, null, bindMap);
  }

  /**
   * Create select statement with specified select clause and from clause. Variant for sub-select
   * which can reference items from super's from clause.
   *
   * @param selectClause defines projection of from clause to results
   * @param fromClause   defines data sources
   * @param whereClause  defines filtering criteria
   * @param parentFrom   is from context from parent select, used in case of sub-selects (sub-select
   *                     used as expression or in EXISTS / IN clause)
   * @param bindMap      if specified, replace bind variables with ones found in this map
   * @return select based on supplied clauses
   */
  S select(SelectClause selectClause, FromClause fromClause, @Nullable Condition whereClause,
      @Nullable FromContext parentFrom, @Nullable BindMap bindMap);

  /**
   * Add from clause built on table, no join - usable for the first table or Oracle-style join.
   *
   * @param tableName is table name, potentially with scheme etc.
   * @param alias     is alias (optional, if not specified, no alias will be used)
   * @return from element based on specified table and alias
   */
  J from(NamePath tableName, @Nullable SimpleName alias);

  /**
   * Create literal. Note that system does not check if given type of literal is supported in this
   * execution engine on creation, but might fail to use it when statement is executed.
   *
   * @param value is value of literal
   * @return new literal
   */
  E literal(Object value);

  /**
   * String literal represented as NVARCHAR2. Must be separate method as both VARCHAR and NVARCHAR
   * correspond to String in Java.
   *
   * @param value is value of literal
   * @return NVarchar literal
   */
  E literalNVarchar(String value);

  /**
   * Call to function specified by enum. Supplied expressions must correspond to supported arguments
   * of function. Resulting expression has value of type, corresponding to function result type
   *
   * @param function    is function to be invoked
   * @param arguments   is list of arguments to be passed to function
   * @param fromContext is from context of enclosing select statement
   * @param bindMap     if specified, replace bind variables with ones found in this map
   * @return expression that evaluates to call to CHR function applied on code
   */
  E function(Function function, Expression[] arguments, @Nullable FromContext fromContext,
      @Nullable BindMap bindMap);

  /**
   * Call to function specified by enum. Supplied expressions must correspond to supported arguments
   * of function. Resulting expression has value of type, corresponding to function result type
   *
   * @param function    is function to be invoked
   * @param arguments   is list of arguments to be passed to function
   * @param fromContext is from context of enclosing select statement
   * @param bindMap     if specified, replace bind variables with ones found in this map
   * @return expression that evaluates to call to CHR function applied on code
   */
  E function(Function function, List<? extends Expression> arguments,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap);

  /**
   * Expression that references table column (source property).
   *
   * @param table       is alias of source from element; will be validated against fromContext,
   *                    generally might not be in default form
   * @param column      is name of column
   * @param type        is type of data contained in column
   * @param fromContext is from context (e.g. available from elements) in which column is used
   * @param bindMap     is map of bind names to variables
   * @return expression in this context, representing column
   */
  E tableColumn(@Nullable NamePath table, SimpleName column, Class<?> type,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap);
}
