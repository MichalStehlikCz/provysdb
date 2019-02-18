package com.provys.provysdb;

import com.provys.common.exception.InternalException;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.LabelableConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Labeling callback implementation - switch to required session
 */
class ProvysConnectionLabelingCallback implements ConnectionLabelingCallback {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(ProvysConnectionLabelingCallback.class.getName());

    static final int EXACT_MATCH = 0;
    static final int TOKEN_MATCH = EXACT_MATCH + 1;
    static final int USER_MATCH = TOKEN_MATCH + 1;
    static final int GENERIC_CONNECTION = USER_MATCH + 1;
    static final int NEW_CONNECTION = GENERIC_CONNECTION + 1;
    static final int REUSE_TOKEN = NEW_CONNECTION + 1;
    static final int REUSE_USER = REUSE_TOKEN + 1;
    static final int REUSE_GENERIC = REUSE_USER + 1;

    static final String PROPERTY_TYPE = "CONNECTION_TYPE";
    static final String CONNECTION_GENERIC = "GENERIC";
    static final String CONNECTION_TOKEN = "TOKEN";
    static final String PROPERTY_TOKEN = "TOKEN";
    static final String CONNECTION_USER = "USER";
    static final String PROPERTY_USER = "USER_ID";

    ProvysConnectionLabelingCallback() {
    }

    /**
     * Implements following preferences (in lowering priority)
     * - if all labels match, use session (EXACT_MATCH)
     * - if token matches, use session (TOKEN_MATCH)
     * - if user matches, use session (USER_MATCH)
     * - if no session, token or user is required, reuse connection without token and user (GENERIC_CONNECTION)
     * - use uninitialized session (NEW_CONNECTION)
     * - reuse session that has token set (REUSE_TOKEN)
     * - reuse session that has user set (REUSE_USER)
     * ... least priority, reuse session without token or user (REUSE_GENERIC)
     *
     * @param reqLabels are labels supplied in call to method
     * @param currentLabels are labels associated with session (connection) being considered
     * @return penalty for using given connection for fulfilling request
     */
    @Override
    public int cost(Properties reqLabels, Properties currentLabels) {
        // exact match
        if (reqLabels.equals(currentLabels)) {
            LOG.debug("Exact connection match ({})", EXACT_MATCH);
            return EXACT_MATCH;
        }
        // new session
        if (currentLabels.getProperty(PROPERTY_TYPE) == null) {
            LOG.debug("New connection ({})", NEW_CONNECTION);
            return NEW_CONNECTION;
        }
        // token matches
        String reqToken = reqLabels.getProperty(PROPERTY_TOKEN);
        String currentToken = currentLabels.getProperty(PROPERTY_TOKEN);
        if ((reqToken != null) && (reqToken.equals(currentToken))) {
            LOG.debug("Token match ({})", TOKEN_MATCH);
            return TOKEN_MATCH;
        }
        // user matches
        String reqUser = reqLabels.getProperty(PROPERTY_USER);
        String currentUser = currentLabels.getProperty(PROPERTY_USER);
        if ((reqUser != null) && (reqUser.equals(currentUser))) {
            LOG.debug("User match ({})", USER_MATCH);
            return USER_MATCH;
        }
        // switch based on connection type
        switch (currentLabels.getProperty(PROPERTY_TYPE)) {
            case CONNECTION_TOKEN:
                LOG.debug("Reuse token connection ({})", REUSE_TOKEN);
                return REUSE_TOKEN;
            case CONNECTION_USER:
                LOG.debug("Reuse user connection ({})", REUSE_USER);
                return REUSE_USER;
            default:
                if ((reqToken == null) && (reqUser == null)) {
                    LOG.debug("Use generic connection ({})", GENERIC_CONNECTION);
                    return GENERIC_CONNECTION;
                } else {
                    LOG.debug("Reuse generic connection ({})", REUSE_GENERIC);
                    return REUSE_GENERIC;
                }
        }
    }

    private void throwNoConnection(LabelableConnection labelableConnection) {
        if (!(labelableConnection instanceof Connection)) {
            throw new InternalException(LOG, "Oracle connection pool sent no-connection for configuration");
        }
    }

    @SuppressWarnings("squid:S1192")
    private void initToken(String token, LabelableConnection labelableConnection)
            throws SQLException {
        throwNoConnection(labelableConnection);
        BigDecimal userId;
        try (var callableStatement = ((Connection) labelableConnection).prepareCall(
                "BEGIN" +
                        "  KER_User_PG.mp_SetUserID(\n" +
                        "        p_TokenID => :c_Token\n" +
                        "      , p_Remove => FALSE\n" +
                        "    );\n" +
                        "  :c_User_ID:=KER_User_PG.mf_GetUserID;" +
                        "  KER_Server_EP.mp_Commit;\n" +
                        "END;")) {
            callableStatement.setString("c_Token", token);
            callableStatement.registerOutParameter("c_User_ID", Types.NUMERIC);
            callableStatement.execute();
            userId = callableStatement.getBigDecimal("c_User_ID");
        }
        labelableConnection.applyConnectionLabel(PROPERTY_TYPE, CONNECTION_TOKEN);
        labelableConnection.applyConnectionLabel(PROPERTY_TOKEN, token);
        labelableConnection.applyConnectionLabel(PROPERTY_USER, userId.toPlainString());
    }

    private void initUser(String userId, LabelableConnection labelableConnection)
            throws SQLException {
        throwNoConnection(labelableConnection);
        try (var callableStatement = ((Connection) labelableConnection).prepareCall(
                "BEGIN" +
                        "  KER_User_PG.mp_SetUserID(\n" +
                        "        p_User_ID => :c_User_ID\n" +
                        "      , p_TestRights => FALSE\n" +
                        "    );\n" +
                        "  KER_Server_EP.mp_Commit;\n" +
                        "END;")) {
            callableStatement.setBigDecimal("c_User_ID", new BigDecimal(userId));
            callableStatement.execute();
        }
        labelableConnection.applyConnectionLabel(PROPERTY_TYPE, CONNECTION_USER);
        labelableConnection.removeConnectionLabel(PROPERTY_TOKEN);
        labelableConnection.applyConnectionLabel(PROPERTY_USER, userId);
    }

    private void initGeneric(LabelableConnection labelableConnection)
            throws SQLException {
        throwNoConnection(labelableConnection);
        BigDecimal userId;
        try (var callableStatement = ((Connection) labelableConnection).prepareCall(
                "BEGIN" +
                        "  KER_User_PG.mp_SetUserID(\n" +
                        "        p_TestRights => FALSE\n" +
                        "    );\n" +
                        "  :c_User_ID:=KER_User_PG.mf_GetUserID;" +
                        "  KER_Server_EP.mp_Commit;\n" +
                        "END;")) {
            callableStatement.registerOutParameter("c_User_ID", Types.NUMERIC);
            callableStatement.execute();
            userId = callableStatement.getBigDecimal("c_User_ID");
        }
        labelableConnection.applyConnectionLabel(PROPERTY_TYPE, CONNECTION_GENERIC);
        labelableConnection.removeConnectionLabel(PROPERTY_TOKEN);
        labelableConnection.applyConnectionLabel(PROPERTY_USER, userId.toPlainString());
    }

    @Override
    public boolean configure(Properties reqLabels, Object conn) {
        try {
            LabelableConnection lconn = (LabelableConnection) conn;
            var currentLabels = lconn.getConnectionLabels();
            if (reqLabels.containsKey(PROPERTY_TOKEN)) {
                // required token connection
                if (reqLabels.getProperty(PROPERTY_TOKEN).equals(currentLabels.getProperty(PROPERTY_TOKEN))) {
                    LOG.debug("Configure: Token match for current session, no action needed");
                } else {
                    LOG.debug("Configure: Initialize connection for token {}",
                            () -> reqLabels.getProperty(PROPERTY_TOKEN));
                    initToken(reqLabels.getProperty(PROPERTY_TOKEN), lconn);
                }
            } else if (reqLabels.containsKey(PROPERTY_USER)) {
                // required user connection
                if (reqLabels.getProperty(PROPERTY_USER).equals(currentLabels.getProperty(PROPERTY_USER))) {
                    LOG.debug("Configure: User match for current connection, no action needed");
                } else {
                    LOG.debug("Configure: Initialize connection for user {}",
                            () -> reqLabels.getProperty(PROPERTY_USER));
                    initUser(reqLabels.getProperty(PROPERTY_USER), lconn);
                }
            } else {
                // required generic connection
                if (CONNECTION_GENERIC.equals(currentLabels.getProperty(PROPERTY_TYPE))) {
                    LOG.debug("Configure: Reusing generic connection, no action needed");
                } else {
                    LOG.debug("Configure: Initialize generic connection");
                    initGeneric(lconn);
                }
            }
        } catch (SQLException e) {
            LOG.warn("SQL exception during connection configuration", e);
            return false;
        }
        return true;
    }

}