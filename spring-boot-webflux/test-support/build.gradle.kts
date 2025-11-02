plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.github.platform.spring-test-conventions")
}

description = "Test support utilities for Spring Boot WebFlux applications"

dependencies {
    // Depend on spring-core for common components
    api(project(":spring-core"))
    api(project(":spring-boot-webflux:starter"))

    // Spring Boot Test
    api("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("io.projectreactor:reactor-test")

    // Spring Security for reactive test configuration
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.security:spring-security-test")

    // Kotlin
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib")
}
