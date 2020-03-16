package com.provys.db.dbcontext.spring;

import com.provys.db.provysdb.ProvysConnectionPoolDataSource;
import com.provys.db.provysdb.ProvysConnectionPoolDataSourceImpl;
import com.provys.db.provysdb.ProvysDbContext;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.provysdb.ProvysDbConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring factory class, producing beans for DbContext library.
 */
@Configuration
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
