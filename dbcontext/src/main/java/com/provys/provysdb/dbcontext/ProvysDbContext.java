package com.provys.provysdb.dbcontext;

import com.provys.common.exception.RegularException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class supports connection to Provys database and gives access to this database via connection (class
 * {@code ProvysConnection})
 *
 * @author stehlik
 */
@SuppressWarnings("unused")
@ApplicationScoped
public class ProvysDbContext {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(ProvysDbContext.class);

    @Nonnull
    private final ProvysConnectionPoolDataSource provysDataSource;
    @Nonnull
    private final Configuration jooqConfiguration;

    /**
     * Default creator for Provys database context.
     * Initializes provys database datasource and builds default JOOQ configuration, that will be used when accessing
     * this database
     *
     * @throws SQLException when initialisation of session pool for connection to PROVYS database fails
     */
    public ProvysDbContext() throws SQLException {
        provysDataSource = buildProvysDBDataSource();
        jooqConfiguration = buildJooqConfiguration();
    }

    @Nonnull
    private ProvysConnectionPoolDataSource buildProvysDBDataSource() throws SQLException {
        return new ProvysConnectionPoolDataSource();
    }

    @Nonnull
    private Configuration buildNoDSJooqConfiguration() {
        return new DefaultConfiguration()
                .set(SQLDialect.ORACLE12C);
    }

    @Nonnull
    private Configuration buildJooqConfiguration() {
        return buildNoDSJooqConfiguration()
                .set(provysDataSource);
    }

    /**
     * Retrieve DSLContext for use by JOOQ to construct and execute queries and statements against Provys database.
     * Context is attached to PROVYS database and has proper logging and other settings.
     *
     * @return context based on provys data source
     */
    @Nonnull
    public DSLContext createDSL() {
        return DSL.using(jooqConfiguration);
    }

    /**
     * Retrieve DSLContext for use by JOOQ to construct and execute queries and statements against Provys database.
     * Context is attached to PROVYS database and has proper logging and other settings.
     *
     * @param dbToken is token stored in database, used to validate user in switch of Provys user context in database
     * @return context based on provys data source
     */
    @Nonnull
    public DSLContext createDSL(String dbToken) {
        try {
            return DSL.using(buildNoDSJooqConfiguration().set(provysDataSource.getConnectionWithToken(
                    Objects.requireNonNull(dbToken))));
        } catch (SQLException e) {
            // we do not want to display token in logs for security reasons
            throw new RegularException(LOG, "PROVYSDB_CANNOTCONNECTWITHTOKEN",
                    "Failed to initialize connection with token", e);
        }
    }

    /**
     * Retrieve connection that can be used to access Provys database. Uses ProvysConnection wrapper that provides
     * monitoring, logging and wrappers around prepared statement and result-set, supporting Provys framework specific
     * classes / mapping to types used in database
     *
     * @return retrieved connection
     */
    @Nonnull
    public ProvysConnection getConnection() {
        try {
            return new ProvysConnectionImpl(provysDataSource.getConnection());
        } catch (SQLException e) {
            throw new RegularException(LOG, "PROVYSDB_CANNOTCONNECT", "Failed to initialize connection", e);
        }
    }

    /**
     * Retrieve connection that can be used to access Provys database. Uses ProvysConnection wrapper that provides
     * monitoring, logging and wrappers around prepared statement and result-set, supporting Provys framework specific
     * classes / mapping to types used in database. Uses token to switch to particular Provys user account
     *
     * @param dbToken is valid token, registered in Provys database
     * @return retrieved connection
     */
    @Nonnull
    public ProvysConnection getConnection(String dbToken) {
        try {
            return new ProvysConnectionImpl(provysDataSource.getConnectionWithToken(Objects.requireNonNull(dbToken)));
        } catch (SQLException e) {
            throw new RegularException(LOG, "PROVYSDB_CANNOTCONNECTWITHTOKEN",
                    "Failed to initialize connection with token", e);
        }
    }

    /**
     * @return username used to open connection to Provys database
     */
    @Nonnull
    public String getUser() {
        return provysDataSource.getUser();
    }

    /**
     * @return URL of database provys data-source is connected to
     */
    @Nonnull
    public String getUrl() {
        return provysDataSource.getUrl();
    }
}
