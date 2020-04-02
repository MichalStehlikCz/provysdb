package com.provys.db.sqldb.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindWithPos;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S1192") // duplicate strings
class SelectStatementImplTest {

  /**
   * Used in this package instead of executed com.provys.db.sql statement.
   */
  private static final String SQL = "test query";

  @Test
  void getBindsTest() {
    var bindName1 = BindName.valueOf("first");
    var bind1 = new BindWithPos(bindName1, Integer.class, List.of(1, 3));
    var bindName2 = BindName.valueOf("second");
    var bind2 = new BindWithPos(bindName2, String.class, List.of(2));
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, List.of(bind1, bind2), Collections.emptyMap(),
        connection)) {
      assertThat(statement.getBinds()).containsExactlyInAnyOrder(bindName1, bindName2);
    }
  }

  @Test
  void bindValueFailTest() {
    var bindName1 = BindName.valueOf("first");
    var bind1 = new BindWithPos(bindName1, Integer.class, List.of(1, 3));
    var bindName2 = BindName.valueOf("second");
    var bind2 = new BindWithPos(bindName2, String.class, List.of(2));
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, List.of(bind1, bind2), Collections.emptyMap(),
        connection)) {
      /* ok bind */
      statement.bindValue("FIRST", 5)
          .bindValue("second", null);
      /* non-existent bind variable */
      assertThatThrownBy(() -> statement.bindValue("third", 5))
          .hasMessageContaining("not found");
      /* incorrect value type */
      assertThatThrownBy(() -> statement.bindValue("first", "value"))
          .hasMessageContaining("type");
    }
  }

  @Test
  void executeTest() throws SQLException {
    var bindName1 = BindName.valueOf("first");
    var bind1 = new BindWithPos(bindName1, Integer.class, List.of(1, 3));
    var bindName2 = BindName.valueOf("second");
    var bind2 = new BindWithPos(bindName2, String.class, List.of(2));
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, List.of(bind1, bind2), Collections.emptyMap(),
        connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      /* first execution - binds on  default values */
      assertThat(statement.execute()).isEqualTo(resultSet);
      verify(preparedStatement).setNullableValue(1, null, Integer.class);
      verify(preparedStatement).setNullableValue(2, null, String.class);
      verify(preparedStatement).setNullableValue(3, null, Integer.class);
      /* second execution - modify value of bind1 */
      statement.bindValue(bindName1, 5);
      assertThat(statement.execute()).isEqualTo(resultSet);
      verify(preparedStatement).setNullableValue(1, 5, Integer.class);
      verify(preparedStatement).setNullableValue(3, 5, Integer.class);
    }
  }

  @Test
  void closeConnectionTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      statement.close();
      verify(preparedStatement).close();
      verifyNoMoreInteractions(preparedStatement);
      //noinspection JDBCResourceOpenedButNotSafelyClosed,resource
      verify(connection).prepareStatement(SQL);
      verifyNoMoreInteractions(connection);
    }
  }

  @Test
  void closeContextTest() throws SQLException {
    var context = mock(DbContext.class);
    var connection = mock(DbConnection.class);
    when(context.getConnection()).thenReturn(connection);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, context)) {
      statement.close();
      verify(preparedStatement).close();
      verifyNoMoreInteractions(preparedStatement);
      //noinspection JDBCResourceOpenedButNotSafelyClosed,resource
      verify(connection).prepareStatement(SQL);
      verify(connection).close();
      verifyNoMoreInteractions(connection);
    }
  }

  @Test
  void fetchOneNoCloseTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, false);
      var expectedResult = "result";
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult);
      var result = statement.fetchOneNoClose((rs, lineNr) -> rs.getNonNullString(1));
      assertThat(result).isEqualTo(expectedResult);
      verify(connection, never()).close();
      verify(preparedStatement, never()).close();
    }
  }

  @Test
  void fetchOneNoCloseTooManyRowsTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, true);
      var expectedResult = "result";
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult);
      assertThatThrownBy(() -> statement.fetchOneNoClose((rs, lineNr) -> rs.getNonNullString(1)))
          .hasMessageContaining("more than one");
      verify(connection, never()).close();
      verify(preparedStatement, never()).close();
    }
  }

  @Test
  void fetchOneNoCloseNoDataFoundTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(false);
      var expectedResult = "result";
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult);
      assertThatThrownBy(() -> statement.fetchOneNoClose((rs, lineNr) -> rs.getNonNullString(1)))
          .hasMessageContaining("no rows");
      verify(connection, never()).close();
      verify(preparedStatement, never()).close();
    }
  }

  @Test
  void fetchNoCloseTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, true, false);
      var expectedResult = new String[]{"result", "other"};
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult[0], expectedResult[1]);
      var result = statement.fetchNoClose((rs, lineNr) -> rs.getNonNullString(1));
      assertThat(result).containsExactly(expectedResult);
      verify(connection, never()).close();
      verify(preparedStatement, never()).close();
    }
  }

  @Test
  void fetchNoCloseEmptyTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(false);
      var result = statement.fetchNoClose((rs, lineNr) -> rs.getNonNullString(1));
      assertThat(result).isEmpty();
      verify(connection, never()).close();
      verify(preparedStatement, never()).close();
    }
  }

  @Test
  void fetchOneTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, false);
      var expectedResult = "result";
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult);
      var result = statement.fetchOne((rs, lineNr) -> rs.getNonNullString(1));
      assertThat(result).isEqualTo(expectedResult);
      verify(preparedStatement).close();
    }
  }

  @Test
  void fetchOneTooManyRowsTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, true);
      var expectedResult = "result";
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult);
      assertThatThrownBy(() -> statement.fetchOne((rs, lineNr) -> rs.getNonNullString(1)))
          .hasMessageContaining("more than one");
      verify(preparedStatement).close();
    }
  }

  @Test
  void fetchOneNoDataFoundTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(false);
      var expectedResult = "result";
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult);
      assertThatThrownBy(() -> statement.fetchOne((rs, lineNr) -> rs.getNonNullString(1)))
          .hasMessageContaining("no rows");
      verify(preparedStatement).close();
    }
  }

  @Test
  void fetchTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, true, false);
      var expectedResult = new String[]{"result", "other"};
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult[0], expectedResult[1]);
      var result = statement.fetch((rs, lineNr) -> rs.getNonNullString(1));
      assertThat(result).containsExactly(expectedResult);
      verify(preparedStatement).close();
    }
  }

  @Test
  void fetchEmptyTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(false);
      var result = statement.fetch((rs, lineNr) -> rs.getNonNullString(1));
      assertThat(result).isEmpty();
      verify(preparedStatement).close();
    }
  }

  @Test
  void streamTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, true, false);
      var expectedResult = new String[]{"result", "other"};
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult[0], expectedResult[1]);
      try (var stream = statement.stream((rs, lineNr) -> rs.getNonNullString(1))) {
        assertThat(stream).containsExactly(expectedResult);
      }
      verify(preparedStatement).close();
    }
  }

  @Test
  void streamNoCloseTest() throws SQLException {
    var connection = mock(DbConnection.class);
    var preparedStatement = mock(DbPreparedStatement.class);
    when(connection.prepareStatement(SQL)).thenReturn(preparedStatement);
    try (var statement = new SelectStatementImpl(SQL, connection)) {
      var resultSet = mock(DbResultSet.class);
      when(preparedStatement.executeQuery()).thenReturn(resultSet);
      when(resultSet.next()).thenReturn(true, true, false);
      var expectedResult = new String[]{"result", "other"};
      when(resultSet.getNonNullString(1)).thenReturn(expectedResult[0], expectedResult[1]);
      try (var stream = statement.streamNoClose((rs, lineNr) -> rs.getNonNullString(1))) {
        assertThat(stream).containsExactly(expectedResult);
      }
      verify(preparedStatement, never()).close();
    }
  }
}