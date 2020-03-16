package com.provys.db.sqldb.sql;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.FromClause;
import com.provys.db.sql.Select;
import com.provys.db.sql.SelectClause;
import com.provys.db.sql.SelectStatement;
import com.provys.db.sql.Context;
import com.provys.db.sql.Condition;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents Sql select statement with default (= Oracle) syntax.
 */
public class SqlSelectImpl implements SqlSelect {

  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;
  private final SqlSelectClause selectClause;
  private final SqlFromClause fromClause;
  private final @Nullable SqlCondition whereClause;
  private final Map<BindName, BindVariable> bindsByName;

  /**
   * Constructor is protected to allow subclassing. For instantiation, SqlFactory uses other
   * constructor.
   *
   * @param context is context this select is valid for
   */
  protected SqlSelectImpl(SqlContext<?, ?, ?, ?, ?, ?, ?> context, SelectClause selectClause,
      FromClause fromClause, @Nullable Condition whereClause, @Nullable BindMap bindMap) {
    this.context = context;
    this.selectClause = selectClause.transfer(context, bindMap);
    this.fromClause = fromClause.transfer(context, bindMap);
    if (whereClause == null) {
      this.whereClause = null;
    } else {
      this.whereClause = whereClause.transfer(context, bindMap);
    }
    // we need to collect binds from sub-elements, as bindMap can contain variables not present in
    // this statement
    this.bindsByName = Map.copyOf(new BindVariableCollector()
        .add(selectClause)
        .add(fromClause)
        .add(whereClause)
        .getBindsByName());
  }

  @Override
  public SqlContext<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return new BindVariableCollector()
        .add(selectClause)
        .add(fromClause)
        .add(whereClause)
        .getBindsByName()
        .values();
  }

  @Override
  public <S extends Select> S transfer(Context<S, ?, ?, ?, ?, ?, ?> targetContext,
      @Nullable BindMap bindMap) {
    if (context.equals(targetContext) && ((bindMap == null) || bindMap.isSupersetOf(bindsByName))) {
      @SuppressWarnings("unchecked")
      var result = (S) this;
      return result;
    }
    return targetContext.select(selectClause, fromClause, whereClause, bindMap);
  }

  /**
   * Map of binds and their values.
   *
   * @return map of bind names and their respective values
   */
  Map<BindName, Object> getBindValues() {
    return bindsByName.values().stream()
        .filter(bindVariable -> bindVariable.getValue() != null)
        .collect(Collectors
            .toUnmodifiableMap(BindVariable::getName, bind -> castNonNull(bind.getValue())));
  }

  @Override
  public void append(CodeBuilder builder) {
    builder.appendLine("SELECT");
    selectClause.append(builder);
    builder.appendLine("FROM");
    fromClause.append(builder);
    if (whereClause != null) {
      builder.append("WHERE");
      whereClause.append(builder);
    }
  }

  @Override
  public SelectStatement parse() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    append(builder);
    return new SelectStatementImpl(builder.build(), builder.getBindsWithPos(), getBindValues(),
        context);
  }

  @Override
  public SelectStatement parse(DbConnection connection) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    append(builder);
    return new SelectStatementImpl(builder.build(), builder.getBindsWithPos(), getBindValues(),
        connection);
  }


}
