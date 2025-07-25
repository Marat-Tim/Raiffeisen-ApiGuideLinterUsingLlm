package io.github.marattim.raif_api_guide.llm_impl.fake;

import io.github.marattim.raif_api_guide.llm_impl.Llm;

public class FakeLlm implements Llm {
    private final String response;

    public FakeLlm(String response) {
        this.response = response;
    }

    @Override
    public Context context(String prompt) {
        return new FakeContext(response);
    }

    public static class FakeContext implements Context {
        private final String response;

        public FakeContext(String response) {
            this.response = response;
        }

        @Override
        public String response(String message) {
            return response;
        }
    }
}
