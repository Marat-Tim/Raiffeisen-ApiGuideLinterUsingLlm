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
            Сколько всего ошибок нашел линтер: %s
            
            Сколько всего ошибок было вручную отмечено в файле: %s
            
            Сколько ошибок, которые нашел линтер соответствуют вручную отмеченным: %s
            
            Сколько ошибок, которые нашел линтер НЕ соответствуют вручную отмеченным: %s
            
            Сколько ошибок, которые были вручную отмечены не нашел линтер: %s
            """.formatted(positiveTotal, trueTotal, truePositive, falsePositive, trueNegative);
    }
}
