package gov.cms.mat.cql.elements;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Builder
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CodeProperties extends BaseProperties {
    // Example: code "Therapy Appropriate (1234)": '1' from "ActCode" display 'Therapy Appropriate'
    private static final String CODE_TEMPLATE = "code \"%s\": '%s' from \"%s\" display '%s'";
    private static final String SUFFIX_TEMPLATE = "code \"%s (%s)\": '%s' from \"%s\" display '%s'";

    String name;
    @Setter
    String code;
    @Setter
    String suffix;
    @Setter
    String codeSystem;
    @Setter
    String display;
    String line;

    @Override
    public void setToFhir() {
        log.trace("Currently a no op");
    }

    @Override
    public String createCql() {
        String converted = String.format(CODE_TEMPLATE, name, code, codeSystem, display);
        return StringUtils.isEmpty(suffix) ? converted : converted + String.format(SUFFIX_TEMPLATE, name, suffix, code, codeSystem, display);
    }
}
