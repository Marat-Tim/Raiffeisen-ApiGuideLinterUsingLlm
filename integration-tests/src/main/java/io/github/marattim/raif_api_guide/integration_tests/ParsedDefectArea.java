package io.github.marattim.raif_api_guide.integration_tests;

/**
 * Местоположение указанной в файле ошибки
 *
 * @see ParsedErrorsFromSpec
 */
public interface ParsedDefectArea {
    /**
     * Диапазон номеров строк, в которых может возникнуть указанная ошибка
     */
    LinesRange lines();

    /**
     * Id указанной ошибки(например, path-kebab-case)
     */
    String id();

    /**
     * Номер, придуманный пользователем(назначение смотри в {@link ParsedErrorsFromSpec}).
     * Если не задан, то пустая строка
     */
    String number();
}
