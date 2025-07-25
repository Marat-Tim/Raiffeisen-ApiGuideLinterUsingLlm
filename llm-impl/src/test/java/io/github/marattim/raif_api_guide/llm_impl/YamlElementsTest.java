package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.SpecPart;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YamlElementsTest {
    @Test
    void shouldCorrectlySplitUsingFactor() {
        Path path = Path.of("./openapi.yaml");
        List<SpecPart> elements = new YamlElements(
            path,
            """
                1
                2
                3
                4
                5
                6
                7
                8
                9
                """,
            4
        ).stream().toList();
        assertTrue(elements.stream().map(SpecPart::path).allMatch(file -> file.equals(path)));
        assertEquals(
            """
                1
                2
                3
                4""",
            elements.get(0).text()
        );
        assertEquals(
            """
                5
                6
                7
                8""",
            elements.get(1).text()
        );
        assertEquals(
            """
                9""",
            elements.get(2).text()
        );
    }
}