package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.integration_tests.fake.FakeDefect;
import io.github.marattim.raif_api_guide.integration_tests.fake.FakeParsedDefectArea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class StatisticsIssueReproductionTest {
    
    @Test
    void shouldNotHaveMoreFoundThanExpected() {
        // Reproduce the exact issue from the problem statement:
        // Total existing errors: 17, but we found 18 existing errors
        // This demonstrates the problem where truePositive > trueTotal
        
        // Create a scenario with multiple duplicates that match the same expected areas
        StatisticsData result = new Statistics(
            List.of(
                // 5 different expected areas (ids: error1, error2, error3, error4, error5)
                new FakeParsedDefectArea(true, "error1"),
                new FakeParsedDefectArea(true, "error2"), 
                new FakeParsedDefectArea(true, "error3"),
                new FakeParsedDefectArea(false, "error4"), // won't match
                new FakeParsedDefectArea(false, "error5")  // won't match
            ),
            List.of(
                // 8 actual defects, with some duplicates
                new FakeDefect("error1"), // matches expected error1
                new FakeDefect("error1"), // duplicate of error1 
                new FakeDefect("error1"), // another duplicate of error1
                new FakeDefect("error2"), // matches expected error2
                new FakeDefect("error2"), // duplicate of error2
                new FakeDefect("error3"), // matches expected error3
                new FakeDefect("unknown1"), // doesn't match any expected
                new FakeDefect("unknown2")  // doesn't match any expected
            )
        ).value();
        
        // With the fix:
        // - positiveTotal: 8 (total actual defects)
        // - trueTotal: 5 (total expected areas)
        // - truePositive: 3 (only 3 unique expected areas were matched: error1, error2, error3)
        // - falsePositive: 5 (2 duplicates of error1, 1 duplicate of error2, plus 2 unknown errors)
        // - trueNegative: 2 (error4 and error5 were not matched)
        
        // The key assertion: truePositive should NEVER exceed trueTotal
        Assertions.assertTrue(
            result.truePositive() <= result.trueTotal(),
            String.format("Found %d existing errors but only %d total errors exist. This indicates duplicate counting.",
                result.truePositive(), result.trueTotal())
        );
        
        // Specific expected values
        Assertions.assertEquals(8, result.positiveTotal(), "Should count all actual defects");
        Assertions.assertEquals(5, result.trueTotal(), "Should count all expected areas");
        Assertions.assertEquals(3, result.truePositive(), "Should count unique matched expected areas");
        Assertions.assertEquals(5, result.falsePositive(), "Should count duplicates and unmatched as false positives");
        Assertions.assertEquals(2, result.trueNegative(), "Should count unmatched expected areas");
    }
}