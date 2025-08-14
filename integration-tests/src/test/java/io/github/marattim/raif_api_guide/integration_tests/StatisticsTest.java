package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.integration_tests.fake.FakeDefect;
import io.github.marattim.raif_api_guide.integration_tests.fake.FakeParsedDefectArea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class StatisticsTest {
    @Test
    void shouldCorrectlyCalculateStatistics() {
        Assertions.assertEquals(
            new StatisticsData(
                2,
                3,
                1,
                1,
                2
            ),
            new Statistics(
                List.of(
                    new FakeParsedDefectArea(true, "first"),
                    new FakeParsedDefectArea(false, "second"),
                    new FakeParsedDefectArea(true, "third")
                ),
                List.of(
                    new FakeDefect("first"),
                    new FakeDefect("second")
                )
            ).value()
        );
    }

}