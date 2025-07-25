package io.github.marattim.raif_api_guide.llm_impl;

/**
 * Информационная таблица из описания правила.
 * | ID              | Severity | Дата принятия |
 * |-----------------|----------|---------------|
 * | path-kebab-case | MUST     | 27.02.2025    |
 */
class InfoTable {
    private final String text;

    InfoTable(String text) {
        this.text = text;
    }

    /**
     * Id указанный в таблице
     */
    String id() {
        String line = text.split("\n")[tableStart(text) + 2];
        return line.substring(2, line.indexOf(' ', 2));
    }

    /**
     * Severity указанный в таблице
     */
    String severity() {
        String line = text.split("\n")[tableStart(text) + 2];
        int index = line.indexOf('|', 1);
        return line.substring(index + 2, line.indexOf(' ', index + 2));
    }

    private static int tableStart(String text) {
        String[] split = text.split("\n");
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("| ID")) {
                return i;
            }
        }
        throw new IllegalStateException("Не найдена строка с таблицей");
    }

}
