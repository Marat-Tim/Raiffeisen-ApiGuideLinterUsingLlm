package io.github.marattim.raif_api_guide;

import java.util.stream.Stream;

/**
 * Спецификация в формате open api
 */
public interface OpenApi {
    /**
     * Возвращает список ошибок в спецификации
     */
    Stream<? extends Defect> defects();

}
