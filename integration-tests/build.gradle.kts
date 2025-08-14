plugins {
    id("java")
}

group = "ru.marattim"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":llm-impl"))
    implementation("dev.langchain4j:langchain4j-open-ai:1.2.0")
    implementation("dev.langchain4j:langchain4j-ollama:1.2.0")
    implementation("com.openai:openai-java:1.3.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}