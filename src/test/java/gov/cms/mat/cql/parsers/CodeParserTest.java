package gov.cms.mat.cql.parsers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeParserTest {

  CodeParser codeParser = () -> new String[0];

  @Test
  void testGeneralCodeParsing() {
    String line = "code \"ExampleCode (123)\": '456' from \"ExampleCS\" display 'Example Display'";
    var codeProperties = codeParser.buildCodeProperties(line);
    assertEquals(line, codeProperties.getLine());
    assertEquals("ExampleCode (123)", codeProperties.getName());
    assertEquals("123", codeProperties.getSuffix());
    assertEquals("456", codeProperties.getCode());
    assertEquals("Example Display", codeProperties.getDisplay());
    assertEquals("ExampleCS", codeProperties.getCodeSystem());
  }
}
