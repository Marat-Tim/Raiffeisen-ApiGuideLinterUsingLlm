package io.github.marattim.raif_api_guide.integration_tests;

import java.util.stream.Collectors;

/**
 * Yaml, очищенный от комментариев, начинающихся с @defects
 */
public class YamlWithoutDefectsComments {
    private final String yaml;

    public YamlWithoutDefectsComments(String yaml) {
        this.yaml = yaml;
    }

    public String value() {
        return yaml.lines()
            .map(line -> {
                if (line.contains("# @defects")) {
                    return line.substring(0, line.indexOf("# @defects")).stripTrailing();
                }
                return line;
            })
            .collect(Collectors.joining("\n"));
    }
}
