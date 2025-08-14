package io.github.marattim.raif_api_guide.integration_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

class ParsedDefectsInSquareBracketsTest {

    @Test
    void shouldCorrectlyParse() {
        Assertions.assertEquals(
            Set.of(
                new OneParsedDefect(
                    1,
                    "some-defect",
                    ""
                ),
                new OneParsedDefect(
                    2,
                    "use-correct-url-sequence",
                    "1"
                ),
                new OneParsedDefect(
                    3,
                    "use-correct-url-sequence",
                    "1"
                )
            ),
            new ParsedDefectsInSquareBrackets(
                """
                    paths: # @defects:[some-defect]
                      /v1/petstore/buy: # @defects:[use-correct-url-sequence:1:start]
                        get: # @defects:[use-correct-url-sequence:1:end]
                    """
            ).stream().collect(Collectors.toSet())
        );
    }
}