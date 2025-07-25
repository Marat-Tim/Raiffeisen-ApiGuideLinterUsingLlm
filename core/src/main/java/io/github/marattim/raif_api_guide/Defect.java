package io.github.marattim.raif_api_guide;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Одна ошибка, найденная в спецификации
 */
public interface Defect {
    /**
     * Позиция в спецификации, где найдена ошибка
     */
    Selection selection();

    /**
     * Id ошибки(например, path-kebab-case)
     */
    String id();

    /**
     * Уровень серьезности ошибки
     */
    Severity severity();

    /**
     * Описание ошибки
     */
    String description();

    /**
     * <b>Полный</b> путь к файлу, в котором содержится ошибка
     */
    Path path();

    /**
     * Предложения по исправлению ошибки(может не быть)
     */
    Stream<? extends Suggestion> suggestions();

}
