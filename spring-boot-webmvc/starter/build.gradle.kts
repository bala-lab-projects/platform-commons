plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.github.platform.spring-web-conventions")
}

description = "Spring Boot WebMVC starter with logging, security, and exception handling"

// Disable bootJar for library module
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}

dependencies {
    // Depend on spring-core for common components
    api(project(":spring-core"))

    // Spring Boot WebMVC
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-aop")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.data:spring-data-commons")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    api("org.slf4j:slf4j-api")

    // Spring Boot manages the version
    api("org.apache.commons:commons-lang3")

    // Kotlin dependencies
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Swagger/OpenAPI for API documentation
    api("io.swagger.core.v3:swagger-annotations:2.2.20")
}
