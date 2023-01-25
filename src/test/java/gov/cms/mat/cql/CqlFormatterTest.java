package gov.cms.mat.cql;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cms.mat.ResourceFileUtil;
import gov.cms.mat.cql.exceptions.CqlFormatterException;
import java.security.Principal;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CqlFormatterTest implements ResourceFileUtil {

    @Test
    void formatCql() {
        String cqlData = getStringFromResource("/cv_populations.cql");

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test.user");

        var result = CqlFormatter.formatCql(cqlData, principal);
        assertTrue(inputMatchesOutput(cqlData, Objects.requireNonNull(result)));
    }

    @Test
    void formatCqlWithMissingModel() {
        String cqlData = getStringFromResource("/missing-model.cql");

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test.user");

        var result = CqlFormatter.formatCql(cqlData, principal);
        assertTrue(inputMatchesOutput(cqlData, Objects.requireNonNull(result)));
    }

    @Test
    void formatCqlWithInvalidSyntax() {
        String cqlData = getStringFromResource("/invalid_syntax.cql");

        Principal principal = mock(Principal.class);
        assertThrows(CqlFormatterException.class, () -> CqlFormatter.formatCql(cqlData, principal));
    }

    @Test
    void formatCqlWithNoData() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test.user");

        var result = CqlFormatter.formatCql("", principal);
        assertTrue(inputMatchesOutput("", Objects.requireNonNull(result)));
    }

    private boolean inputMatchesOutput(String input, String output) {
        return input
            .replaceAll("[\\s\\u0000\\u00a0]", "")
            .equals(output.replaceAll("[\\s\\u0000\\u00a0]", ""));
    }
}