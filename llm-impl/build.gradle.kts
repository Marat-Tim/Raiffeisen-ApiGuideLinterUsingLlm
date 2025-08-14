plugins {
    `java-library`
}

group = "io.github.marattim"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    api("dev.langchain4j:langchain4j:1.2.0")
    api("dev.langchain4j:langchain4j-core:1.2.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.11.0")
}

tasks.test {
    useJUnitPlatform()
}