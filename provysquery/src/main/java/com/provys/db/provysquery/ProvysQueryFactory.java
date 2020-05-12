package com.provys.db.provysquery;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.provysdb.AdminDbContext;
import com.provys.db.querybuilder.ElementBuilderFactory;
import com.provys.db.sqlquery.query.DefaultStatementFactory;
import com.provys.db.sqlquery.query.StatementFactory;
import com.provys.db.sqlquerybuilder.AdminQueryBuilderFactory;
import com.provys.db.sqlquerybuilder.DefaultSqlQueryBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring bean factory, producing SqlQueryBuilderFactory connected to Provys database.
 */
@Configuration
@Immutable
public class ProvysQueryFactory {

  @Bean("AdminStatementFactory")
  StatementFactory adminStatementFactory(AdminDbContext dbContext) {
    return new DefaultStatementFactory(dbContext);
  }

  @Bean
  AdminQueryBuilderFactory adminQueryBuilderFactory(
      @Qualifier("AdminStatementFactory") StatementFactory statementFactory) {
    return new DefaultSqlQueryBuilderFactory(statementFactory, ElementBuilderFactory.getInstance());
  }
}
