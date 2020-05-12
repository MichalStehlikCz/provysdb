package com.provys.db.provysdb;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.provysdb.AdminDbContext;
import com.provys.db.provysdb.ProvysConnectionPoolDataSource;
import com.provys.db.provysdb.ProvysConnectionPoolDataSourceImpl;
import com.provys.db.provysdb.ProvysDbConfiguration;
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
  AdminDbContext adminDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    return new AdminDbContext(provysDataSource);
  }

  @Override
  public String toString() {
    return "DbContextFactory{}";
  }
}
