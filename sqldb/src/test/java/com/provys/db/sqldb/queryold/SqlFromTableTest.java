package com.provys.db.sqldb.queryold;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.query.BindVariable;
import com.provys.db.query.Function;
import com.provys.db.query.SegmentedName;
import com.provys.db.query.SimpleName;
import com.provys.db.sqldb.codebuilder.CodeBuilderFactory;
import org.junit.jupiter.api.Test;

class SqlFromTableTest {
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