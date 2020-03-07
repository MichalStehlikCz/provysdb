package com.provys.provysdb.dbsqlbuilder.jakarta;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbsqlbuilder.impl.SqlAdminImpl;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
class DbSqlBuilderFactory {

  @Produces
  @ApplicationScoped
  SqlAdminImpl getSqlAdmin(DbContext dbContext) {
    return new SqlAdminImpl(dbContext);
  }
}
