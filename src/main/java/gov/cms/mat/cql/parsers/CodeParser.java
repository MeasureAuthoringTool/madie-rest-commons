package gov.cms.mat.cql.parsers;

import gov.cms.mat.cql.elements.CodeProperties;
import gov.cms.mat.cql.elements.ValueSetProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public interface CodeParser extends OidParser, CommentParser {
    String[] getLines();

    default List<CodeProperties> getCodes() {
        AtomicBoolean isInComment = new AtomicBoolean(false);

        return Arrays.stream(getLines())
                .filter(l -> !lineComment(l, isInComment))
                .filter(l -> l.startsWith("code"))
                .map(this::buildCodeProperties)
                .collect(Collectors.toList());
    }

    default CodeProperties buildCodeProperties(String line) {
      return CodeProperties.builder()
                .line(line)
                .name(findName(line))
                .code(findOid(line))
                .suffix(findCodeSuffix(findName(line)))
                .codeSystem(findCodeSystem(line))
                .display(findDisplay(line))
                .build();
    }

  default String findCodeSuffix(String codeName) {
    String suffix  = StringUtils.substringBetween(codeName, " (", ")");
    if (StringUtils.isNumeric(suffix)) {
      return suffix;
    } else {
      return "";
    }
  }

  default String findCodeSystem(String line) {
        return StringUtils.substringBetween(line, "from \"", "\"");
  }

  default String findDisplay(String line) {
        return StringUtils.substringBetween(line, "display '", "'");
  }
}
