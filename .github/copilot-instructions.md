# Raiffeisen API Guide Linter Using LLM

This is a Java-based API Guide Linter that uses Large Language Models (LLM) to validate OpenAPI specifications against Raiffeisen Bank's API guidelines. The linter downloads rules from the official Raiffeisen API guide and uses LangChain4j to analyze OpenAPI files for compliance violations.

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Build and Test Commands
- **Clean the project**: `./gradlew clean` - takes ~1 second
- **Build the project (compile only)**: `./gradlew assemble` - takes 1-2 seconds. NEVER CANCEL. Set timeout to 120+ seconds for safety.
- **Run specific module tests**:
  - `./gradlew :core:test :integration-tests:test` - takes ~2 seconds. These tests ALWAYS PASS.
  - **WARNING**: `./gradlew :llm-impl:test` - HAS 1 FAILING TEST. Use `./gradlew :llm-impl:test --continue` if you need to run it.
- **Run all checks**: `./gradlew check` - FAILS due to the failing test in llm-impl module
- **Full build with tests**: `./gradlew build` - FAILS due to the failing test in llm-impl module

### Java Version and Setup
- **Java Version**: OpenJDK 17 (Temurin)
- **Build Tool**: Gradle 8.12 (auto-downloads on first use)
- **First-time setup**: The Gradle wrapper will automatically download Gradle (~25 seconds) and dependencies

### Key Project Modules
- **core**: Contains core interfaces (`OpenApi`, `Defect`, `Selection`, etc.) - no implementation, always builds successfully
- **llm-impl**: Main implementation using LangChain4j for LLM integration - has 1 failing test but compiles successfully
- **integration-tests**: Quality analysis scripts for testing prompts and LLM models - requires external LLM API access

### Working with Tests
- **Known Issue**: `OpenApiUsingLlmTest.shouldReturnDefectParsedFromLlmResponse()` in llm-impl module ALWAYS FAILS
  - This is a known issue with character position assertions (expects 0, gets 1)
  - The test uses mock LLM responses and should not require external API calls
  - **WORKAROUND**: Use `./gradlew assemble` for builds or `./gradlew :core:test :integration-tests:test` for testing
  - **Individual working tests**: Use `./gradlew :llm-impl:test --tests RulesFromGithubTest -q` to run specific passing tests
- **Integration tests**: All pass but some require environment variables (`LLM_API_URL`, `LLM_API_KEY`) for actual LLM testing

## Validation Scenarios

### Basic Validation After Changes
1. **Always build first**: `./gradlew assemble` - ensures compilation works
2. **Run working tests**: `./gradlew :core:test :integration-tests:test` - validates core functionality
3. **Check specific functionality**: Look at the integration-tests for examples of how the linter works with real OpenAPI specs

### Manual Testing Scenarios
- **Check API rules parsing**: The `RulesFromGithubTest` validates that 23 rules are correctly parsed from the Raiffeisen API guide
  - Run individually: `./gradlew :llm-impl:test --tests RulesFromGithubTest -q`
- **Validate defect detection**: The integration tests show how defects are marked in YAML comments like `# @defects:[rule-id]`
  - Example from example.yaml: `# @defects:[path-kebab-case,method-operation-id-camel-case]`
- **Sample OpenAPI file**: `/integration-tests/src/main/resources/example.yaml` contains a sample OpenAPI spec with marked defects
- **Test specific functionality**: Run individual test classes using `--tests ClassName` to isolate functionality

### Integration Testing
- **Run experiments**: The `AllModelsTest` main class can test different LLM models but requires:
  - `LLM_API_URL` environment variable
  - `LLM_API_KEY` environment variable
- **Usage**: `./gradlew :integration-tests:run` (if configured) or run the main class directly

## Common Tasks and Troubleshooting

### Repository Structure
```
.
├── README.md                   # Project overview and motivation
├── settings.gradle.kts         # Gradle multi-module configuration
├── gradlew                     # Gradle wrapper (executable)
├── core/                       # Core interfaces and abstractions
│   └── src/main/java/          # OpenApi, Defect, Selection interfaces
├── llm-impl/                   # Main LLM implementation
│   ├── src/main/java/          # LangChain4j-based implementation
│   └── src/test/java/          # Tests (1 failing test)
└── integration-tests/          # Quality analysis and experiments
    ├── src/main/java/          # Experiment classes and statistics
    ├── src/main/resources/     # Sample OpenAPI specs
    └── src/test/java/          # Parsing and validation tests
```

### Key Files to Check When Making Changes
- **Core interfaces**: Always check `core/src/main/java/io/github/marattim/raif_api_guide/` after interface changes
- **Rule parsing**: Check `llm-impl/src/main/java/io/github/marattim/raif_api_guide/llm_impl/rule/` when modifying rule handling
- **LLM integration**: Check `llm-impl/src/main/java/io/github/marattim/raif_api_guide/llm_impl/` for prompt and model changes

### Dependencies and External Resources
- **Raiffeisen API Rules**: Downloaded from `https://raw.githubusercontent.com/Raiffeisen-DGTL/rest-api-guide/refs/heads/main/rules/rules.md`
- **LangChain4j**: Used for LLM integration (version 1.2.0)
- **OpenAI Java Client**: Used for OpenAI API integration (version 1.3.1)

### Expected Build Times and Timeouts
- **Clean build**: 1-2 seconds
- **Incremental build**: < 1 second
- **Test execution**: 1-2 seconds for working modules
- **CRITICAL**: NEVER CANCEL builds or tests. Set timeouts to 120+ seconds minimum even though actual times are much shorter.

### Error Handling
- **Compilation errors**: Always fixable - the project has a stable build configuration
- **Test failures**: Expected in llm-impl module - use workarounds mentioned above
- **Dependency issues**: Rare - all dependencies are stable and well-defined
- **Network issues**: May affect rule downloading in RulesFromGithub class during tests

## Project Purpose and Context
This linter addresses the need for automated validation of OpenAPI specifications against Raiffeisen Bank's API guidelines. It provides:
- **Automated defect detection** with line-level precision
- **LLM-powered analysis** for complex rule validation
- **Extensible rule system** that downloads current guidelines from GitHub
- **Integration capability** for IDEs and build systems