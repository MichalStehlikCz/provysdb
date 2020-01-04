package com.provys.provysdb.dbcontext.impl;

import com.provys.common.exception.RegularException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbcontext.type.SqlTypeFactory;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class supports connection to Provys database and gives access to this database via connection (class
 * {@code DbConnection}). Simplified wrapper around actual data source, wraps retrieved connections in ProvysConnection
 * wrapper, potentially providing monitoring for database calls
 *
 * @author stehlik
 */
@SuppressWarnings("unused")
@ApplicationScoped
public class ProvysDbContext implements DbContext {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(ProvysDbContext.class);

    @Nonnull
    private final ProvysConnectionPoolDataSource provysDataSource;
    @Nonnull
    private final SqlTypeMap sqlTypeMap;

    /**
     * Default creator for Provys database context.
     *
     * @param provysDataSource is DataSource used to access Provys Oracle database
     */
    @Inject
    public ProvysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
        this.provysDataSource = Objects.requireNonNull(provysDataSource);
        sqlTypeMap = new SqlTypeFactory().getDefaultMap();
    }

    @Override
    @Nonnull
    public DbConnection getConnection() {
        try {
            return new ProvysConnection(provysDataSource.getConnection(), sqlTypeMap);
        } catch (SQLException e) {
            throw new RegularException(LOG, "PROVYSDB_CANNOTCONNECT", "Failed to initialize connection", e);
        }
    }

    @Override
    @Nonnull
    public DbConnection getConnection(String dbToken) {
        try {
            return new ProvysConnection(provysDataSource.getConnectionWithToken(Objects.requireNonNull(dbToken)),
                    sqlTypeMap);
        } catch (SQLException e) {
            throw new RegularException(LOG, "PROVYSDB_CANNOTCONNECTWITHTOKEN",
                    "Failed to initialize connection with token", e);
        }
    }

    @Override
    @Nonnull
    public String getUser() {
        return provysDataSource.getUser();
    }

    @Override
    @Nonnull
    public String getUrl() {
        return provysDataSource.getUrl();
    }

    @Nonnull
    @Override
    public SqlTypeMap getSqlTypeMap() {
        return sqlTypeMap;
    }
}
