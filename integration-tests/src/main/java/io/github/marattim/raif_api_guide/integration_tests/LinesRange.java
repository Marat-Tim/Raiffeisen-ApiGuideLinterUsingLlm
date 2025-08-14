package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Selection;

/**
 * Диапазон строк
 */
public interface LinesRange {
    boolean contains(Selection selection);
}
