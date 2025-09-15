package gov.cms.mat.cql.parsers;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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

    @Test
    public void testGetLinesWithSingleLine() {
        // given
        String cql = "library Test version '1.0.0'";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        assertThat(lines.length, is(equalTo(1)));
        assertThat(lines[0], is(equalTo("library Test version '1.0.0'")));
    }

    @Test
    public void testGetLinesWithMultipleLines() {
        // given
        String cql = "library Test version '1.0.0'\nusing FHIR version '4.0.1'\ninclude SomeLib version '1.2.3'";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        assertThat(lines.length, is(equalTo(3)));
        assertThat(lines[0], is(equalTo("library Test version '1.0.0'")));
        assertThat(lines[1], is(equalTo("using FHIR version '4.0.1'")));
        assertThat(lines[2], is(equalTo("include SomeLib version '1.2.3'")));
    }

    @Test
    public void testGetLinesWithEmptyString() {
        // given
        String cql = "";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        assertThat(lines.length, is(equalTo(1)));
        assertThat(lines[0], is(equalTo("")));
    }

    @Test
    public void testGetLinesWithWindowsLineEndings() {
        // given
        String cql = "line1\r\nline2\r\nline3";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        assertThat(lines.length, is(equalTo(3)));
        assertThat(lines[0], is(equalTo("line1")));
        assertThat(lines[1], is(equalTo("line2")));
        assertThat(lines[2], is(equalTo("line3")));
    }

    @Test
    public void testGetLinesWithTrailingNewline() {
        // given
        String cql = "line1\nline2\n";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        assertThat(lines.length, is(equalTo(2)));
        assertThat(lines[0], is(equalTo("line1")));
        assertThat(lines[1], is(equalTo("line2")));
    }

    @Test
    public void testGetLinesWithOnlyNewlines() {
        // given
        String cql = "\n\n";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        String[] lines = parser.getLines();
        // then
        assertThat(lines.length, is(equalTo(0)));
    }

    @Test
    public void testGetAllUsingsWithMultipleUsings() {
        // given
        String cql = "library Test version '1.0.0'\nusing USCore version '7.0.0'\nusing QICore version '7.0.0'\nusing Other version '2.0'";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        var usings = parser.getAllUsings();
        // then
        assertThat(usings.size(), is(equalTo(3)));
        assertThat(usings.get(0).getLibraryType(), is(equalTo("USCore")));
        assertThat(usings.get(0).getVersion(), is(equalTo("7.0.0")));
        assertThat(usings.get(1).getLibraryType(), is(equalTo("QICore")));
        assertThat(usings.get(1).getVersion(), is(equalTo("7.0.0")));
        assertThat(usings.get(2).getLibraryType(), is(equalTo("Other")));
        assertThat(usings.get(2).getVersion(), is(equalTo("2.0")));
    }

    @Test
    public void testGetAllUsingsWithCommentsAndBlankLines() {
        // given
        String cql = "// This is a comment\nusing USCore version '7.0.0'\n\n/* block comment */\nusing QICore version '7.0.0'\nusing Other version '4.0.0' // trailing comment";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        var usings = parser.getAllUsings();
        // then
        assertThat(usings.size(), is(equalTo(3)));
        assertThat(usings.get(0).getLibraryType(), is(equalTo("USCore")));
        assertThat(usings.get(0).getVersion(), is(equalTo("7.0.0")));
        assertThat(usings.get(1).getLibraryType(), is(equalTo("QICore")));
        assertThat(usings.get(1).getVersion(), is(equalTo("7.0.0")));
        assertThat(usings.get(2).getLibraryType(), is(equalTo("Other")));
        assertThat(usings.get(2).getVersion(), is(equalTo("4.0.0")));
        assertThat(usings.get(2).getLine(), is(equalTo("using Other version '4.0.0' // trailing comment")));
    }

    @Test
    public void testGetAllUsingsWithNoUsings() {
        // given
        String cql = "library Test version '1.0.0'\ninclude SomeLib version '1.2.3'";
        UsingParserImpl parser = new UsingParserImpl(cql);
        // when
        var usings = parser.getAllUsings();
        // then
        assertThat(usings.size(), is(equalTo(0)));
    }
}
