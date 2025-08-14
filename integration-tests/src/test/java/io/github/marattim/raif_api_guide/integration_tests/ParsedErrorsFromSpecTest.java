package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.integration_tests.fake.EmptySelectionOnLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class ParsedErrorsFromSpecTest {
    @Test
    void shouldParseSimpleDefect() {
        Assertions.assertTrue(
            new ParsedErrorsFromSpec(
                """
                    paths:
                      /v1/api/petsList: # @defects:[path-kebab-case]
                        get:
                    """
            ).stream().findFirst().orElseThrow().lines().contains(
                new EmptySelectionOnLine(2)
            )
        );
    }

    @Test
    void shouldParseMultipleDefects() {
        List<ParsedDefectArea> areas = new ParsedErrorsFromSpec(
            """
                paths:
                  /v1/api/petsList: # @defects:[path-kebab-case,path-no-redundant-prefixes]
                    get:
                """
        ).stream().toList();
        Assertions.assertTrue(
            areas.stream().anyMatch(
                area -> area.lines().contains(
                    new EmptySelectionOnLine(2)
                )
            )
        );
        Assertions.assertTrue(
            areas.stream().anyMatch(
                area -> area.lines().contains(
                    new EmptySelectionOnLine(2)
                )
            )
        );
    }

    @Test
    void shouldParseDefectArea() {
        ParsedDefectArea area = new ParsedErrorsFromSpec(
            """
                paths:
                  /v1/petstore/buy: # @defects:[use-correct-url-sequence:1:start]
                    get: # @defects:[use-correct-url-sequence:1:end]
                """
        ).stream().findFirst().orElseThrow();
        Assertions.assertTrue(area.lines().contains(new EmptySelectionOnLine(2)));
        Assertions.assertTrue(area.lines().contains(new EmptySelectionOnLine(3)));
    }

    @Test
    void shouldParseAllIds() {
        Assertions.assertEquals(
            Set.of(
                "some-defect",
                "use-correct-url-sequence"
            ),
            new ParsedErrorsFromSpec(
                """
                    paths: # @defects:[some-defect]
                      /v1/petstore/buy: # @defects:[use-correct-url-sequence:1:start]
                        get: # @defects:[use-correct-url-sequence:1:end]
                    """
            ).stream().map(ParsedDefectArea::id).collect(Collectors.toSet())
        );
    }
}