package com.provys.db.provysdb;

import com.google.errorprone.annotations.Immutable;
import com.provys.auth.api.UserContext;
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

  @Bean
  UserDbContext userDbContext(ProvysConnectionPoolDataSource provysDataSource,
      UserContext userContext) {
    return new UserDbContext(provysDataSource, userContext);
  }

  @Override
  public String toString() {
    return "DbContextFactory{}";
  }
}
