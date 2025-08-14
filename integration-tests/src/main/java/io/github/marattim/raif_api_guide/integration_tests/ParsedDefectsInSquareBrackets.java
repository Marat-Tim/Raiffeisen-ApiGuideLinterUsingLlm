package io.github.marattim.raif_api_guide.integration_tests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ParsedDefectsInSquareBrackets {
    private static final Pattern pattern = Pattern.compile("@defects:\\[(.*?)]");

    private final String yaml;

    public ParsedDefectsInSquareBrackets(String yaml) {
        this.yaml = yaml;
    }

    public Stream<OneParsedDefect> stream() {
        List<String> lines = yaml.lines().toList();
        List<OneParsedDefect> defects = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("@defects")) {
                Matcher matcher = pattern.matcher(lines.get(i));
                if (matcher.find()) {
                    for (String defect : matcher.group(1).split(",")) {
                        if (defect.contains(":")) {
                            String[] split = defect.split(":");
                            defects.add(
                                new OneParsedDefect(
                                    i + 1,
                                    split[0],
                                    split[1]
                                )
                            );
                        } else {
                            defects.add(
                                new OneParsedDefect(
                                    i + 1,
                                    defect,
                                    ""
                                )
                            );
                        }
                    }
                }
            }
        }
        return defects.stream();
    }
}
