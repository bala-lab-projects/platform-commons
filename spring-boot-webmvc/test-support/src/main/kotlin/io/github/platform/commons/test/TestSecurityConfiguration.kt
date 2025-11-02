package io.github.platform.commons.test

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

/**
 * Test security configuration that disables security for integration tests.
 * Import this configuration in your test classes when you need to bypass security.
 */
@TestConfiguration
@EnableWebSecurity
class TestSecurityConfiguration {
    /**
     * Configures security to permit all requests for testing.
     *
     * @throws Exception if configuration fails
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
            .csrf { it.disable() }
            .build()
}
