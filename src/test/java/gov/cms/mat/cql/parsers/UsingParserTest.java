package gov.cms.mat.cql.parsers;

import org.junit.jupiter.api.Test;

public class UsingParserTest {

    public static class UsingParserImpl implements UsingParser {
        private final String[] lines;

        public UsingParserImpl(String cql) {
            this.lines = cql.split("\\r?\\n");
        }

        @Override
        public String[] getLines() {
            return lines;
        }
    }

    @org.junit.jupiter.api.Test
    public void testGetLinesWithSingleLine() {
        // given
        String cql = "library Test version '1.0.0'";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        org.hamcrest.MatcherAssert.assertThat(lines.length, org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo(1)));
        org.hamcrest.MatcherAssert.assertThat(lines[0], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("library Test version '1.0.0'")));
    }

    @org.junit.jupiter.api.Test
    public void testGetLinesWithMultipleLines() {
        // given
        String cql = "library Test version '1.0.0'\nusing FHIR version '4.0.1'\ninclude SomeLib version '1.2.3'";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        org.hamcrest.MatcherAssert.assertThat(lines.length, org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo(3)));
        org.hamcrest.MatcherAssert.assertThat(lines[0], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("library Test version '1.0.0'")));
        org.hamcrest.MatcherAssert.assertThat(lines[1], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("using FHIR version '4.0.1'")));
        org.hamcrest.MatcherAssert.assertThat(lines[2], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("include SomeLib version '1.2.3'")));
    }

    @org.junit.jupiter.api.Test
    public void testGetLinesWithEmptyString() {
        // given
        String cql = "";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        org.hamcrest.MatcherAssert.assertThat(lines.length, org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo(1)));
        org.hamcrest.MatcherAssert.assertThat(lines[0], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("")));
    }

    @org.junit.jupiter.api.Test
    public void testGetLinesWithWindowsLineEndings() {
        // given
        String cql = "line1\r\nline2\r\nline3";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        org.hamcrest.MatcherAssert.assertThat(lines.length, org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo(3)));
        org.hamcrest.MatcherAssert.assertThat(lines[0], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("line1")));
        org.hamcrest.MatcherAssert.assertThat(lines[1], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("line2")));
        org.hamcrest.MatcherAssert.assertThat(lines[2], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("line3")));
    }

    @org.junit.jupiter.api.Test
    public void testGetLinesWithTrailingNewline() {
        // given
        String cql = "line1\nline2\n";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        org.hamcrest.MatcherAssert.assertThat(lines.length, org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo(2)));
        org.hamcrest.MatcherAssert.assertThat(lines[0], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("line1")));
        org.hamcrest.MatcherAssert.assertThat(lines[1], org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo("line2")));
    }

    @org.junit.jupiter.api.Test
    public void testGetLinesWithOnlyNewlines() {
        // given
        String cql = "\n\n";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        org.hamcrest.MatcherAssert.assertThat(lines.length, org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalTo(0)));
    }


}
