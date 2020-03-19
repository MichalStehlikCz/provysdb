package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.sql.SimpleName;
import org.junit.jupiter.api.Test;

class SqlFromTableTest {

  @Test
  void getAliasTest() {
    var context = mock(SqlContext.class);
    var alias = SimpleName.valueOf("al");
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"), alias);
    assertThat(fromElem.getAlias()).isEqualTo(alias);
  }

  @Test
  void getAliasNoAliasTest() {
    var context = mock(SqlContext.class);
    var tableName = SimpleName.valueOf("tablename");
    var fromElem = new SqlFromTable(context, tableName, null);
    assertThat(fromElem.getAlias()).isEqualTo(tableName);
  }

  @Test
  void appendTest() {
    var context = mock(SqlContext.class);
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"),
        SimpleName.valueOf("al"));
    var builder = CodeBuilderFactory.getCodeBuilder();
    fromElem.append(builder);
    assertThat(builder.build()).isEqualTo("tablename al");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void appendNoAliasTest() {
    var context = mock(SqlContext.class);
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"), null);
    var builder = CodeBuilderFactory.getCodeBuilder();
    fromElem.append(builder);
    assertThat(builder.build()).isEqualTo("tablename");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }
}