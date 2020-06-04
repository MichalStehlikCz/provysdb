package com.provys.db.crypt;

import com.provys.db.defaultdb.types.SqlTypeAdapter;
import com.provys.db.defaultdb.types.SqlTypeModule;
import java.util.Collection;
import java.util.List;

/**
 * Register type adapters via service loader.
 */
public class CryptSqlTypeModule implements SqlTypeModule {

  /**
   * Collection of adapters, registered by this module.
   *
   * @return collection of sql type adapters, registered by this module.
   */
  @Override
  public Collection<SqlTypeAdapter<?>> getAdapters() {
    return List.of(SqlTypeAdapterEncryptedString.getInstance());
  }
}
