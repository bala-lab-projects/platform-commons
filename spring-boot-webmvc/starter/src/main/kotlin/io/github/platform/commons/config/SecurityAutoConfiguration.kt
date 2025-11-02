package io.github.platform.commons.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Autoconfiguration for security features.
 * Enables AuthorizationAspect for method-level authorization checks.
 */
@Configuration
@ConditionalOnProperty(
    prefix = "platform.security",
    name = ["enabled"],
    matchIfMissing = true,
)
@ComponentScan("io.github.platform.commons.security")
class SecurityAutoConfiguration
