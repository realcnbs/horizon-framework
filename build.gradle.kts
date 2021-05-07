plugins {
    id("java")
}

group = "com.realcnbs"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    compileOnly("org.mybatis:mybatis:3.5.7")
    compileOnly("org.springframework:spring-context:5.3.6")
    compileOnly("org.springframework.data:spring-data-commons:2.5.0")
    compileOnly("org.springframework:spring-beans:5.3.6")
    compileOnly("jakarta.validation:jakarta.validation-api:2.0.2")
    compileOnly("org.apache.commons:commons-lang3:3.6")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.12.3")
    compileOnly("com.google.guava:guava:27.1-jre")

    compileOnly("org.projectlombok:lombok:1.18.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}