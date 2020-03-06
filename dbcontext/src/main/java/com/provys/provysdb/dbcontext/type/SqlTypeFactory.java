package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.SqlTypeMap;

/**
 * Class given access to sql type adapter maps.
 */
public class SqlTypeFactory {

  private static final SqlTypeMap DEFAULT_MAP = new SqlTypeMapImpl(
      SqlTypeAdapterBigDecimal.getInstance(),
      SqlTypeAdapterBigInteger.getInstance(),
      SqlTypeAdapterBoolean.getInstance(),
      SqlTypeAdapterByte.getInstance(),
      SqlTypeAdapterInteger.getInstance(),
      SqlTypeAdapterString.getInstance(),
      SqlTypeAdapterDtUid.getInstance()
  );

  /**
   * Retrieve sql type adapter map, containing sql type adapters, defined in this library.
   * BigDecimal, BigInteger, Boolean (converts boolean to Y/N according to Provys conventions),
   * Byte, Integer, String, DtUid
   *
   * @return default sql type adapter map
   */
  public SqlTypeMap getDefaultMap() {
    return DEFAULT_MAP;
  }
}
