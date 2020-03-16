package com.provys.provysdb.sqldefault;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sql.BindName;
import com.provys.provysdb.sql.Literal;
import com.provys.provysdb.sql.NamePath;
import com.provys.provysdb.sql.SimpleName;
import com.provys.provysdb.sql.SqlFactory;
import com.provys.provysdb.sqldefault.expression.ExpressionFactory;
import com.provys.db.sqldb.sql.name.NameFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public class SqlFactoryDefault implements SqlFactory {

  private static final SqlFactoryDefault INSTANCE = new SqlFactoryDefault(NameFactory.getInstance(),
      ExpressionFactory.getInstance());

  public static SqlFactoryDefault getInstance() {
    return INSTANCE;
  }

  private final NameFactory nameFactory;
  private final ExpressionFactory expressionFactory;

  public SqlFactoryDefault(NameFactory nameFactory, ExpressionFactory expressionFactory) {
    this.nameFactory = nameFactory;
    this.expressionFactory = expressionFactory;
  }

  @Override
  public SimpleName name(String text) {
    return nameFactory.name(text);
  }

  @Override
  public NamePath path(
      Collection<SimpleName> segments) {
    return nameFactory.path(segments);
  }

  @Override
  public BindName bind(String name) {
    return nameFactory.bind(name);
  }

  @Override
  public Literal<String> literal(String value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Byte> literal(byte value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Short> literal(short value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Integer> literal(int value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Long> literal(long value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<BigInteger> literal(BigInteger value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Float> literal(float value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Double> literal(double value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<BigDecimal> literal(BigDecimal value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<Boolean> literal(boolean value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<DtUid> literal(
      DtUid value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<DtDate> literal(
      DtDate value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<DtDateTime> literal(
      DtDateTime value) {
    return expressionFactory.literal(value);
  }

  @Override
  public Literal<String> literalNVarchar(String value) {
    return expressionFactory.literalNVarchar(value);
  }

  @Override
  public String toString() {
    return "SqlFactoryDefault{"
        + "nameFactory=" + nameFactory
        + ", expressionFactory=" + expressionFactory
        + '}';
  }
}
