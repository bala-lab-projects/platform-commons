plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.github.platform.java-conventions")
}

repositories {
    mavenCentral()
    mavenLocal()
}

description = "Core common components for both WebMVC and WebFlux"

// Apply Spring Boot's dependency management
apply(plugin = "io.spring.dependency-management")

dependencies {
    // Spring Boot BOM for dependency management (exported to consumers)
    api(platform("org.springframework.boot:spring-boot-dependencies:3.5.7"))
    api("org.springframework.data:spring-data-commons")

    // Kotlin
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib")

    // Jackson for JSON
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("com.fasterxml.jackson.core:jackson-databind")
    api("com.fasterxml.jackson.core:jackson-annotations")

    // Swagger/OpenAPI for API documentation
    compileOnly("io.swagger.core.v3:swagger-annotations:2.2.20")

    // SLF4J for logging
    api("org.slf4j:slf4j-api")

    // Spring Framework (core only, not Boot)
    compileOnly("org.springframework:spring-context")
    compileOnly("org.springframework:spring-web")

    // Spring Data Commons (for PageableDto, PageImpl, Pageable)
    api("org.springframework.data:spring-data-commons")

    // Spring Boot (for metrics auto-configuration)
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework.boot:spring-boot-starter-actuator")

    // Micrometer for metrics (framework-agnostic, works with both WebMVC and WebFlux)
    compileOnly("io.micrometer:micrometer-core")

    // Validation API
    compileOnly("jakarta.validation:jakarta.validation-api")
}
