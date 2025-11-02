package io.github.platform.commons.annotation

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.web.bind.annotation.RestController

/**
 * Meta-annotation for REST controllers that require authentication.
 * Combines @RestController with Swagger security annotations when swagger-annotations are on the classpath.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RestController
@SecurityScheme(
    name = "OauthToken",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    `in` = SecuritySchemeIn.HEADER,
)
@SecurityRequirement(name = "OauthToken")
annotation class BaseAuthController
