import org.gradle.api.artifacts.dsl.LockMode
plugins {
    id("java")
    id("maven-publish")
}

group = "com.realcnbs"
version = "1.0.14"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
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
    annotationProcessor("org.projectlombok:lombok:1.18.48")

    compileOnly("org.mybatis:mybatis:3.6.0")
    compileOnly("org.springframework:spring-context:7.0.10")
    compileOnly("org.springframework.data:spring-data-commons:4.1.2")
    compileOnly("org.springframework:spring-beans:7.0.10")
    compileOnly("jakarta.validation:jakarta.validation-api:3.1.1")
    compileOnly("org.apache.commons:commons-lang3:3.22.0")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.23")
    compileOnly("com.google.guava:guava:33.8.0-jre")

    compileOnly("org.projectlombok:lombok:1.18.48")

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.1.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

val copyGenericMappersToResources = tasks.register<Copy>("copyGenericMappersToResources") {
    from("src/main/java/com/realcnbs/horizon/framework/data/mapper")
    include("**/*.xml")
    into("$buildDir/resources/main/com/realcnbs/horizon/framework/data/mapper")
}

tasks.withType<ProcessResources> {
    dependsOn(copyGenericMappersToResources)
}

publishing {
    publications {
        create<MavenPublication>("horizon-framework") {
            from(components["java"])
        }
    }
}
dependencyLocking {
    lockAllConfigurations()
    lockMode.set(LockMode.STRICT)
}
val resolvedConfigurations = configurations.filter { it.isCanBeResolved }
tasks.register("resolveDependencies") {
    doLast { resolvedConfigurations.forEach { it.resolve() } }
}
