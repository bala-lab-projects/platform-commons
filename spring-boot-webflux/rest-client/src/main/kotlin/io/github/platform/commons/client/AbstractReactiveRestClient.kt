package io.github.platform.commons.client

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * Abstract base class for reactive REST clients using WebClient.
 * Provides common functionality for making HTTP requests with OAuth authentication.
 */
abstract class AbstractReactiveRestClient(
    protected val webClient: WebClient,
    protected val baseUrl: String,
) {
    /**
     * Performs a GET request and returns the response as a Mono.
     *
     * @param path The relative path.
     * @param headers Additional headers to be added to the request.
     * @return The response as a Mono.
     */
    protected inline fun <reified T> get(
        path: String,
        headers: Map<String, String> = emptyMap(),
    ): Mono<T> {
        logger.debug(GET_REQUEST_TO, baseUrl, path)

        var requestSpec =
            webClient
                .get()
                .uri("$baseUrl$path")

        headers.forEach { (key, value) ->
            requestSpec = requestSpec.header(key, value)
        }

        return requestSpec
            .retrieve()
            .bodyToMono(T::class.java)
            .doOnSuccess { logger.debug(GET_REQUEST_SUCCESS, baseUrl, path) }
            .doOnError { error -> logger.error(GET_REQUEST_FAILED, baseUrl, path, error) }
    }

    /**
     * Performs a POST request and returns the response as a Mono.
     *
     * @param path The relative path.
     * @param body The request body.
     * @param headers Additional headers to be added to the request.
     * @return The response as a Mono.
     */
    protected inline fun <reified T, reified R> post(
        path: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
    ): Mono<R> {
        logger.debug(POST_REQUEST_TO, baseUrl, path)

        var requestSpec =
            webClient
                .post()
                .uri("$baseUrl$path")

        headers.forEach { (key, value) ->
            requestSpec = requestSpec.header(key, value)
        }

        return requestSpec
            .bodyValue(body as Any)
            .retrieve()
            .bodyToMono(R::class.java)
            .doOnSuccess { logger.debug(POST_REQUEST_SUCCESS, baseUrl, path) }
            .doOnError { error -> logger.error(POST_REQUEST_FAILED, baseUrl, path, error) }
    }

    /**
     * Performs a PUT request and returns the response as a Mono.
     *
     * @param path The relative path.
     * @param body The request body.
     * @param headers Additional headers to be added to the request.
     * @return The response as a Mono.
     */
    protected inline fun <reified T, reified R> put(
        path: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
    ): Mono<R> {
        logger.debug(PUT_REQUEST_TO, baseUrl, path)

        var requestSpec =
            webClient
                .put()
                .uri("$baseUrl$path")

        headers.forEach { (key, value) ->
            requestSpec = requestSpec.header(key, value)
        }

        return requestSpec
            .bodyValue(body as Any)
            .retrieve()
            .bodyToMono(R::class.java)
            .doOnSuccess { logger.debug(PUT_REQUEST_SUCCESS, baseUrl, path) }
            .doOnError { error -> logger.error(PUT_REQUEST_FAILED, baseUrl, path, error) }
    }

    /**
     * Performs a DELETE request and returns a Mono<Void>.
     *
     * @param path The relative path.
     * @param headers Additional headers to be added to the request.
     * @return A Mono<Void>.
     */
    protected fun delete(
        path: String,
        headers: Map<String, String> = emptyMap(),
    ): Mono<Void> {
        logger.debug(DELETE_REQUEST_TO, baseUrl, path)

        var requestSpec =
            webClient
                .delete()
                .uri("$baseUrl$path")

        headers.forEach { (key, value) ->
            requestSpec = requestSpec.header(key, value)
        }

        return requestSpec
            .retrieve()
            .bodyToMono(Void::class.java)
            .doOnSuccess { logger.debug(DELETE_REQUEST_SUCCESS, baseUrl, path) }
            .doOnError { error -> logger.error(DELETE_REQUEST_FAILED, baseUrl, path, error) }
    }

    protected val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        // Protected constants can be accessed by protected inline functions
        protected const val GET_REQUEST_TO = "GET request to: {}{}"
        protected const val GET_REQUEST_SUCCESS = "GET request successful: {}{}"
        protected const val GET_REQUEST_FAILED = "GET request failed: {}{}"

        protected const val POST_REQUEST_TO = "POST request to: {}{}"
        protected const val POST_REQUEST_SUCCESS = "POST request successful: {}{}"
        protected const val POST_REQUEST_FAILED = "POST request failed: {}{}"

        protected const val PUT_REQUEST_TO = "PUT request to: {}{}"
        protected const val PUT_REQUEST_SUCCESS = "PUT request successful: {}{}"
        protected const val PUT_REQUEST_FAILED = "PUT request failed: {}{}"

        protected const val DELETE_REQUEST_TO = "DELETE request to: {}{}"
        protected const val DELETE_REQUEST_SUCCESS = "DELETE request successful: {}{}"
        protected const val DELETE_REQUEST_FAILED = "DELETE request failed: {}{}"
    }
}
