package com.provys.provysdb.dbcontext;

import com.provys.common.exception.InternalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static com.provys.provysdb.dbcontext.ProvysConnectionLabelingCallback.*;

class ProvysConnectionLabelingCallbackTest {

    private static final Logger LOG = LogManager.getLogger(ProvysConnectionLabelingCallbackTest.class.getName());

    @SuppressWarnings("squid:S3878")
    @Nonnull
    static Stream<Object[]> costTest() {
        return Stream.of(
                new Object[]{"", "", EXACT_MATCH}
                , new Object[]{"", PROPERTY_TYPE + "=" + CONNECTION_GENERIC, GENERIC_CONNECTION}
                , new Object[]{"", PROPERTY_TYPE + "=" + CONNECTION_TOKEN + "\n"
                        + PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + PROPERTY_USER + "=1000000000000098", REUSE_TOKEN}
                , new Object[]{"", PROPERTY_TYPE + "=" + CONNECTION_USER + "\n"
                        + PROPERTY_USER + "=1000000000000098", REUSE_USER}
                , new Object[]{PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", "", NEW_CONNECTION}
                , new Object[]{PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", PROPERTY_TYPE + "=" + CONNECTION_GENERIC, REUSE_GENERIC}
                , new Object[]{PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", PROPERTY_TYPE + "=" + CONNECTION_TOKEN + "\n"
                        + PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + PROPERTY_USER + "=1000000000000098", TOKEN_MATCH}
                , new Object[]{PROPERTY_TOKEN + "=mdfnwseijtfhuie", PROPERTY_TYPE + "=" + CONNECTION_TOKEN + "\n"
                        + PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + PROPERTY_USER + "=1000000000000098", REUSE_TOKEN}
                , new Object[]{PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", PROPERTY_TYPE + "=" + CONNECTION_USER + "\n"
                        + PROPERTY_USER + "=1000000000000098", REUSE_USER}
                , new Object[]{PROPERTY_USER + "=1000000000000098", "", NEW_CONNECTION}
                , new Object[]{PROPERTY_USER + "=1000000000000098", PROPERTY_TYPE + "=" + CONNECTION_GENERIC, REUSE_GENERIC}
                , new Object[]{PROPERTY_USER + "=1000000000000098", PROPERTY_TYPE + "=" + CONNECTION_TOKEN + "\n"
                        + PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + PROPERTY_USER + "=1000000000000098", USER_MATCH}
                , new Object[]{PROPERTY_USER + "=1000000000000098", PROPERTY_TYPE + "=" + CONNECTION_TOKEN + "\n"
                        + PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + PROPERTY_USER + "=1000000000000100", REUSE_TOKEN}
                , new Object[]{PROPERTY_USER + "=1000000000000098", PROPERTY_TYPE + "=" + CONNECTION_USER + "\n"
                        + PROPERTY_USER + "=1000000000000098", USER_MATCH}
                , new Object[]{PROPERTY_USER + "=1000000000000098", PROPERTY_TYPE + "=" + CONNECTION_USER + "\n"
                        + PROPERTY_USER + "=1000000000000100", REUSE_USER}
        );
    }

    @ParameterizedTest
    @MethodSource
    void costTest(String reqLabelsString, String currentLabelsString, int result) {
        try (
                var reqLabelsReader = new StringReader(reqLabelsString);
                var currentLabelsReader = new StringReader(currentLabelsString)) {
            var reqLabels = new Properties();
            reqLabels.load(reqLabelsReader);
            var currentLabels = new Properties();
            currentLabels.load(currentLabelsReader);
            assertThat(new ProvysConnectionLabelingCallback().cost(reqLabels, currentLabels)).isEqualTo(result);
        } catch (IOException e) {
            throw new InternalException(LOG, "Error reading labels", e);
        }
    }

}