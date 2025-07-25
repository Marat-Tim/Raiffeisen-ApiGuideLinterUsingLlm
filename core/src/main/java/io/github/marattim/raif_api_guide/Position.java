package io.github.marattim.raif_api_guide;

/**
 * Позиция в тексте
 */
public interface Position {
    /**
     * Номер строки(начиная с 1)
     */
    int line();

    /**
     * Номер символа в строке(начиная с 1)
     */
    int character();

}
