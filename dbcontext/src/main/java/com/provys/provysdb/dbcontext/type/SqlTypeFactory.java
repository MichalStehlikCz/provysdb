package com.provys.provysdb.dbcontext.type;

/**
 * Class given access to sql type adapter maps
 */
public class SqlTypeFactory {

    private static final SqlTypeMap DEFAULT_MAP = new SqlTypeMapImpl(
            new SqlTypeAdapterBigDecimal(),
            new SqlTypeAdapterBigInteger(),
            new SqlTypeAdapterBoolean(),
            new SqlTypeAdapterByte(),
            new SqlTypeAdapterInteger(),
            new SqlTypeAdapterString()
            );

    /**
     * Retrieve sql type adapter map, containing sql type adapters, defined in this library: BigDecimal, BigInteger,
     * Boolean (converts boolean to Y/N according to Provys conventions), Byte, Integer, String
     *
     * @return default sql type adapter map
     */
    public SqlTypeMap getDefaultMap() {
        return DEFAULT_MAP;
    }
}
