package com.provys.db.dbcontext.spring;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.provysdb.ProvysConnectionPoolDataSource;
import com.provys.db.provysdb.ProvysConnectionPoolDataSourceImpl;
import com.provys.db.provysdb.ProvysDbConfiguration;
import com.provys.db.provysdb.ProvysDbContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring factory class, producing beans for DbContext library.
 */
@Configuration
@Immutable
public class DbContextFactory {

  @Bean
  ProvysConnectionPoolDataSource provysDbDataSource(ProvysDbConfiguration dbConfiguration) {
    return new ProvysConnectionPoolDataSourceImpl(dbConfiguration);
  }

  @Bean
  DbContext provysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    return new ProvysDbContext(provysDataSource);
  }

  @Override
  public String toString() {
    return "DbContextFactory{}";
  }
}
