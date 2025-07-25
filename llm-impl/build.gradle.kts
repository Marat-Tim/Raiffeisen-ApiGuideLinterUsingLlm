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
    implementation("com.openai:openai-java:1.3.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.1.1")
}

tasks.test {
    useJUnitPlatform()
}