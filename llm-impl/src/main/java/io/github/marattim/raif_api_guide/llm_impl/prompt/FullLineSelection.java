package io.github.marattim.raif_api_guide.llm_impl.prompt;

import io.github.marattim.raif_api_guide.Position;
import io.github.marattim.raif_api_guide.Selection;

/**
 * Выделение всей строки
 */
class FullLineSelection implements Selection {
    private final int number;
    private final String line;

    FullLineSelection(int number, String line) {
        this.number = number;
        this.line = line;
    }

    @Override
    public Position start() {
        return new SimplePosition(number, 1);
    }

    @Override
    public Position end() {
        return new SimplePosition(number, line.length());
    }
}
