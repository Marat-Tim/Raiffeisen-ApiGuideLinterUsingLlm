package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.integration_tests.fake.FakeDefect;
import io.github.marattim.raif_api_guide.integration_tests.fake.FakeParsedDefectArea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class StatisticsDuplicateTest {
    
    @Test
    void shouldNotDoubleCountDuplicateMatches() {
        // Scenario: 3 expected areas, but 2 actual defects that both match the same expected area
        // This should not result in more found existing errors than total existing errors
        StatisticsData result = new Statistics(
            List.of(
                new FakeParsedDefectArea(true, "error1"),  // This matches both actual defects
                new FakeParsedDefectArea(false, "error2"), // This matches no actual defects  
                new FakeParsedDefectArea(false, "error3")  // This matches no actual defects
            ),
            List.of(
                new FakeDefect("error1"),  // This matches first expected area
                new FakeDefect("error1")   // This also matches first expected area (duplicate)
            )
        ).value();
        
        // Expected results:
        // - positiveTotal: 2 (total actual defects found)
        // - trueTotal: 3 (total expected areas) 
        // - truePositive: 1 (only 1 expected area was actually matched, despite 2 actual defects)
        // - falsePositive: 1 (1 actual defect that's a duplicate/false positive)
        // - trueNegative: 2 (2 expected areas not matched)
        
        Assertions.assertEquals(
            new StatisticsData(
                2,  // positiveTotal
                3,  // trueTotal  
                1,  // truePositive - should be 1, not 2
                1,  // falsePositive - should be 1, not 0  
                2   // trueNegative
            ),
            result,
            "Statistics should not double-count when multiple actual defects match the same expected area"
        );
    }
}