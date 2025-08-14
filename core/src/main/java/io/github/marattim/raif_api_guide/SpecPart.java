package io.github.marattim.raif_api_guide;

import java.nio.file.Path;

/**
 * Часть спецификации open api
 * <p/>
 * Нужна, так как может не хватить токенов,
 * чтобы анализировать всю спецификацию за раз
 */
public interface SpecPart {
    /**
     * Текст части спецификации
     */
    String text();

    /**
     * Номер строки, на которой начинается данная часть спецификации
     */
    int line();

    /**
     * <b>Полный</b> путь к файлу, в котором содержится ошибка
     */
    Path path();
}
