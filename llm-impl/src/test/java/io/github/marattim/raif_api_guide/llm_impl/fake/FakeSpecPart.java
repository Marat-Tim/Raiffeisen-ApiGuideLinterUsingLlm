package io.github.marattim.raif_api_guide.llm_impl.fake;

import io.github.marattim.raif_api_guide.SpecPart;

import java.nio.file.Path;

public class FakeSpecPart implements SpecPart {
    @Override
    public String text() {
        return """
            /front-api/limit/enrich:
              post:
                tags:
                  - limit-controller
                operationId: enrich
                requestBody:
                  content:
                    application/json:
                      schema:
                        $ref: "#/components/schemas/EnrichLimitDto"
                  required: true
                responses:
                  "200":
                    description: OK
            """;
    }

    @Override
    public int line() {
        return 10;
    }

    @Override
    public Path path() {
        return Path.of("./openapi.yaml");
    }
}
