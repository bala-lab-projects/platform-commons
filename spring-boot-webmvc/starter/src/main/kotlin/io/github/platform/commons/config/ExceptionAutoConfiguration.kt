package io.github.platform.commons.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Autoconfiguration for exception handling.
 * Enables GlobalExceptionHandler for standardized error responses.
 */
@Configuration
@ConditionalOnProperty(
    prefix = "platform.exception",
    name = ["enabled"],
    matchIfMissing = true,
)
@ComponentScan("io.github.platform.commons.exception")
class ExceptionAutoConfiguration
