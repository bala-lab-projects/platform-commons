plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.github.platform.spring-test-conventions")
}

description = "Test support utilities for Spring Boot WebMVC applications"

dependencies {
    // Depend on spring-core for common components
    api(project(":spring-core"))
    api(project(":spring-boot-webmvc:starter"))

    // Spring Boot Test
    api("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.boot:spring-boot-starter-web")

    // Spring Security for test configuration
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.security:spring-security-test")

    // Kotlin
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib")
}
