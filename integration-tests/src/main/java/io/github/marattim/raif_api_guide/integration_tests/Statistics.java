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
        int positiveTotal = 0;
        int trueTotal = 0;
        int truePositive = 0;
        int falsePositive = 0;
        int trueNegative = 0;
        main:
        for (Defect defect : actual) {
            ++positiveTotal;
            for (ParsedDefectArea area : expected) {
                if (area.lines().contains(defect.selection()) && area.id().equals(defect.id())) {
                    ++truePositive;
                    continue main;
                }
            }
            ++falsePositive;
        }
        main:
        for (ParsedDefectArea area : expected) {
            ++trueTotal;
            for (Defect defect : actual) {
                if (area.lines().contains(defect.selection()) && area.id().equals(defect.id())) {
                    continue main;
                }
            }
            ++trueNegative;
        }
        return new StatisticsData(positiveTotal, trueTotal, truePositive, falsePositive, trueNegative);
    }
}
