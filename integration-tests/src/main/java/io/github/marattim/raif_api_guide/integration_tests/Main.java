package io.github.marattim.raif_api_guide.integration_tests;

public class Main {
    public static void main(String[] args) {
        new Experiment(
            new ResultPrintStream().value()
        ).run();
    }
}
