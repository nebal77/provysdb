package com.provys.provysdb.sqlbuilder.jakarta;

import com.provys.provysdb.sqlbuilder.impl.NoDbSqlImpl;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
class SqlBuilderFactory {

  @Produces
  @ApplicationScoped
  NoDbSqlImpl getNoDbSql() {
    return new NoDbSqlImpl(new DefaultSqlTokenizer());
  }
}
