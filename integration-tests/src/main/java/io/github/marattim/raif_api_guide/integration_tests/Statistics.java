package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Defect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Статистика по результатам тестирования.
 * Список подсчитываемых данных смотри в {@link StatisticsData}
 */
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

        Set<ParsedDefectArea> founded = new HashSet<>();
        int truePositive = 0;
        int falsePositive = 0;

        main:
        for (Defect defect : actual) {
            for (ParsedDefectArea area : expected) {
                if (area.lines().contains(defect.selection()) && area.id().equals(defect.id())) {
                    if (!founded.contains(area)) {
                        founded.add(area);
                        truePositive++;
                        continue main;
                    }
                }
            }
            falsePositive++;
        }

        int trueNegative = trueTotal - founded.size();
        
        return new StatisticsData(positiveTotal, trueTotal, truePositive, falsePositive, trueNegative);
    }
}
