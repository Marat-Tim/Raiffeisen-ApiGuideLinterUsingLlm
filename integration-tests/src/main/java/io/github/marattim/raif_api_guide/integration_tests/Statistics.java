package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Defect;

import java.util.Collection;

public class Statistics {
    private final Collection<ParsedDefectArea> expected;
    private final Collection<Defect> actual;

    public Statistics(Collection<ParsedDefectArea> expected, Collection<Defect> actual) {
        this.expected = expected;
        this.actual = actual;
    }

    public StatisticsData value() {
        int positiveTotal = actual.size();
        int trueTotal = expected.size();
        
        java.util.Set<ParsedDefectArea> matchedExpected = new java.util.HashSet<>();
        int truePositive = 0;
        int falsePositive = 0;
        
        for (Defect defect : actual) {
            boolean foundMatch = false;
            for (ParsedDefectArea area : expected) {
                if (area.lines().contains(defect.selection()) && area.id().equals(defect.id())) {
                    if (!matchedExpected.contains(area)) {
                        // First match for this expected area
                        matchedExpected.add(area);
                        truePositive++;
                        foundMatch = true;
                        break;
                    } else {
                        // This expected area was already matched - this is a duplicate
                        foundMatch = false;
                        break;
                    }
                }
            }
            if (!foundMatch) {
                falsePositive++;
            }
        }
        
        int trueNegative = trueTotal - matchedExpected.size();
        
        return new StatisticsData(positiveTotal, trueTotal, truePositive, falsePositive, trueNegative);
    }
}
