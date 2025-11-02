plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.github.platform.spring-webflux-conventions")
}

description = "Reactive REST client (WebClient) with OAuth support for Spring Boot WebFlux"

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

    // Spring Boot WebFlux
    api("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    // SLF4J
    api("org.slf4j:slf4j-api")

    // Kotlin
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("io.projectreactor.kotlin:reactor-kotlin-extensions")
}
