package com.provys.provysdb.dbsqlbuilder.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbsqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.BindName;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class SelectStatementTImplTest {

  private static class SelectStatementTest extends SelectStatementTImpl<SelectStatementTest> {

    @SuppressWarnings("SameParameterValue")
    SelectStatementTest(String sqlText, Collection<? extends BindName> binds,
        DbConnection connection) {
      super(sqlText, binds, connection);
    }

    @Override
    SelectStatementTest self() {
      return this;
    }
  }

  private static final String testSql = "test sql";

  @Test
  @SuppressWarnings({"resource", "JDBCResourceOpenedButNotSafelyClosed"})
  void testExecute() throws SQLException {
    var resultSet = mock(DbResultSet.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    var connection = mock(DbConnection.class);
    when(connection.prepareStatement(testSql)).thenReturn(preparedStatement);
    var selectStatement = new SelectStatementTest(testSql, Collections.emptyList(), connection);
    assertThat(selectStatement.execute()).isEqualTo(resultSet);
    verify(preparedStatement, times(1)).executeQuery();
    verifyNoMoreInteractions(preparedStatement);
  }

  @Test
  @SuppressWarnings({"resource", "JDBCResourceOpenedButNotSafelyClosed"})
  void testExecuteWithBind() throws SQLException {
    var resultSet = mock(DbResultSet.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    var connection = mock(DbConnection.class);
    when(connection.prepareStatement(testSql)).thenReturn(preparedStatement);
    var bind = mock(BindVariable.class);
    when(bind.getName()).thenReturn("bind1");
    var selectStatement = new SelectStatementTest(testSql, List.of(bind), connection);
    assertThat(selectStatement.execute()).isEqualTo(resultSet);
    verify(preparedStatement, times(1)).executeQuery();
    verifyNoMoreInteractions(preparedStatement);
    verify(bind, atLeast(0)).getName();
    verify(bind, times(1)).bind(preparedStatement, 1);
    verifyNoMoreInteractions(bind);
  }
}