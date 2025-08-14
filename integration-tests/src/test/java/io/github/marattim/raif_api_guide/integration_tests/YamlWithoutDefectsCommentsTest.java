package io.github.marattim.raif_api_guide.integration_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YamlWithoutDefectsCommentsTest {
    @Test
    void shouldDeleteComments() {
        Assertions.assertEquals(
            """
                val:
                  q: 1
                  b: 1""",
            new YamlWithoutDefectsComments(
                """
                    val:
                      q: 1 # @defects:[1]
                      b: 1   # @defects:[problem]
                    """
            ).value()
        );
    }

}