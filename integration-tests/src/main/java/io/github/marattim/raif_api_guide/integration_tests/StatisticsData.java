package io.github.marattim.raif_api_guide.integration_tests;

import org.jspecify.annotations.NonNull;

public record StatisticsData(
    int positiveTotal,
    int trueTotal,
    int truePositive,
    int falsePositive,
    int trueNegative
) {
    @NonNull
    @Override
    public String toString() {
        return """
            Сколько всего нашли ошибок: %s; \
            Сколько всего ошибок было: %s; \
            Существующие ошибки, которые мы нашли: %s; \
            НЕ существующие ошибки, которые мы нашли: %s; \
            существующие ошибки, которые мы НЕ нашли: %s"""
            .formatted(positiveTotal, trueTotal, truePositive, falsePositive, trueNegative);
    }
}
