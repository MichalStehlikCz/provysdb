package com.provys.provysdb.dbcontext.jakarta;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbcontext.impl.ProvysConnectionPoolDataSource;
import com.provys.provysdb.dbcontext.impl.ProvysConnectionPoolDataSourceImpl;
import com.provys.provysdb.dbcontext.impl.ProvysDbConfiguration;
import com.provys.provysdb.dbcontext.impl.ProvysDbContext;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
class DbContextFactory {

  @Produces
  @ApplicationScoped
  ProvysConnectionPoolDataSource provysDbDataSource(ProvysDbConfiguration dbConfiguration) {
    return new ProvysConnectionPoolDataSourceImpl(dbConfiguration);
  }

  @Produces
  @ApplicationScoped
  DbContext provysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    return new ProvysDbContext(provysDataSource);
  }
}
