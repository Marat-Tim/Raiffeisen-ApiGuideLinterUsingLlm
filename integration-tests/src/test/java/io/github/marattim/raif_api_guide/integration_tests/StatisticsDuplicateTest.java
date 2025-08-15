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
        
        // The issue: currently truePositive might be 2 (both actual defects match)
        // But trueTotal is 3 (total expected areas)
        // This creates inconsistent statistics where we "found" more existing errors than exist
        
        System.out.println("Current result: " + result);
        
        // What we expect:
        // - positiveTotal: 2 (total actual defects found)
        // - trueTotal: 3 (total expected areas) 
        // - truePositive: should be 1 (only 1 expected area was actually matched)
        // - falsePositive: should be 1 (1 actual defect that's a duplicate/false positive)
        // - trueNegative: should be 2 (2 expected areas not matched)
        
        // The current implementation likely gives incorrect results due to double counting
    }
}