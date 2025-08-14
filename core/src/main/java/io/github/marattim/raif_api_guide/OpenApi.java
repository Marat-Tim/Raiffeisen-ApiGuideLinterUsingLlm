package io.github.marattim.raif_api_guide;

import java.util.stream.Stream;

/**
 * Спецификация в формате open api
 */
public interface OpenApi {
    /**
     * Возвращает список ошибок в спецификации
     * <p/>
     * Так как предполагается реализация через Llm,
     * которая может тормозить или сломаться, то
     * ошибки должны возвращаться потоком лениво
     */
    Stream<? extends Defect> defects();

}
