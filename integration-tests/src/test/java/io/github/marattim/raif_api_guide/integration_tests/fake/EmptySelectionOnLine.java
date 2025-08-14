package io.github.marattim.raif_api_guide.integration_tests.fake;

import io.github.marattim.raif_api_guide.Position;
import io.github.marattim.raif_api_guide.Selection;

public class EmptySelectionOnLine implements Selection {
    private final int line;

    public EmptySelectionOnLine(int line) {
        this.line = line;
    }

    @Override
    public Position start() {
        return new Position() {
            @Override
            public int line() {
                return line;
            }

            @Override
            public int character() {
                return 1;
            }
        };
    }

    @Override
    public Position end() {
        return new Position() {
            @Override
            public int line() {
                return line;
            }

            @Override
            public int character() {
                return 1;
            }
        };
    }
}
