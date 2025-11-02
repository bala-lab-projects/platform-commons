package io.github.platform.commons.annotation

import io.github.platform.commons.config.ExceptionAutoConfiguration
import io.github.platform.commons.config.LoggingAutoConfiguration
import io.github.platform.commons.config.SecurityAutoConfiguration
import org.springframework.context.annotation.Import

/**
 * Meta-annotation that enables all platform features for a WebFlux (reactive) application.
 * Importing this annotation on your @SpringBootApplication class will automatically configure:
 * - Reactive logging with context propagation
 * - Method-level authorization
 * - Global reactive exception handling with Mono<ResponseEntity<ServiceError>>
 *
 * Note: Unlike WebMVC, async configuration is not needed as WebFlux is inherently asynchronous.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(
    LoggingAutoConfiguration::class,
    SecurityAutoConfiguration::class,
    ExceptionAutoConfiguration::class,
)
annotation class BaseWebFluxApp
