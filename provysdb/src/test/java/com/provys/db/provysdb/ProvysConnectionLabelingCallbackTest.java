package com.provys.db.provysdb;

import static org.assertj.core.api.Assertions.*;

import com.provys.common.exception.InternalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.stream.Stream;

class ProvysConnectionLabelingCallbackTest {

    @SuppressWarnings("squid:S3878")
    static Stream<Object[]> costTest() {
        return Stream.of(
                new Object[]{"", "", ProvysConnectionLabelingCallback.EXACT_MATCH}
                , new Object[]{"", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '=' + ProvysConnectionLabelingCallback.CONNECTION_GENERIC, ProvysConnectionLabelingCallback.GENERIC_CONNECTION}
                , new Object[]{"", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '=' + ProvysConnectionLabelingCallback.CONNECTION_TOKEN + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.REUSE_TOKEN}
                , new Object[]{"", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '=' + ProvysConnectionLabelingCallback.CONNECTION_USER + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.REUSE_USER}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", "", ProvysConnectionLabelingCallback.NEW_CONNECTION}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_GENERIC, ProvysConnectionLabelingCallback.REUSE_GENERIC}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_TOKEN + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.TOKEN_MATCH}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=mdfnwseijtfhuie", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_TOKEN + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.REUSE_TOKEN}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_USER + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.REUSE_USER}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", "", ProvysConnectionLabelingCallback.NEW_CONNECTION}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_GENERIC, ProvysConnectionLabelingCallback.REUSE_GENERIC}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_TOKEN + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.USER_MATCH}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_TOKEN + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_TOKEN + "=xsqjkhfweqiufhnejngfv\n"
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000100", ProvysConnectionLabelingCallback.REUSE_TOKEN}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_USER + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.USER_MATCH}
                , new Object[]{ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000098", ProvysConnectionLabelingCallback.PROPERTY_TYPE + '='
                + ProvysConnectionLabelingCallback.CONNECTION_USER + '\n'
                        + ProvysConnectionLabelingCallback.PROPERTY_USER + "=1000000000000100", ProvysConnectionLabelingCallback.REUSE_USER}
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
            throw new InternalException("Error reading labels", e);
        }
    }

}