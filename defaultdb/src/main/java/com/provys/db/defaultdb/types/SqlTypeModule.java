package com.provys.db.defaultdb.types;

import java.util.Collection;

/**
 * Interface used to register sql type adapters supported by given library using service loader.
 */
public interface SqlTypeModule {

  /**
   * Collection of adapters, registered by this module.
   *
   * @return collection of sql type adapters, registered by this module.
   */
  Collection<SqlTypeAdapter<?>> getAdapters();
}
