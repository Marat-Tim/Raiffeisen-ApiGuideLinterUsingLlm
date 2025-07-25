package io.github.marattim.raif_api_guide;

/**
 * Выделенная часть документа
 */
public interface Selection {
    /**
     * Начало выделения
     */
    Position start();

    /**
     * Конец выделения включительно
     */
    Position end();
}
