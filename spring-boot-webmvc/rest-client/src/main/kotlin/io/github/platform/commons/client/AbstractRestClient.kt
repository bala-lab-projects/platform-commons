package io.github.platform.commons.client

import io.github.platform.commons.client.oauth.OAuthTokenManager
import io.github.platform.commons.constants.HeaderConstants
import io.github.platform.commons.constants.MdcKeys
import io.github.platform.commons.exception.ResourceNotFoundException
import io.github.platform.commons.exception.ValidationException
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

/**
 * Abstract base class for REST clients providing common HTTP operations with OAuth token
 * management and header propagation.
 */
abstract class AbstractRestClient(
    protected val restTemplate: RestTemplate,
    protected val oAuthTokenManager: OAuthTokenManager? = null,
) {
    /**
     * Get the base URL for this client.
     *
     * @return Base URL.
     */
    protected abstract fun getBaseUrl(): String

    /**
     * Performs HTTP GET request.
     *
     * @param path The relative path.
     * @param responseType The response type.
     * @return The response entity.
     */
    protected fun <T> get(
        path: String,
        responseType: Class<T>,
    ): ResponseEntity<T> = exchange(path, HttpMethod.GET, null, responseType)

    /**
     * Performs HTTP POST request.
     *
     * @param path The relative path.
     * @param body The request body.
     * @param responseType The response type.
     * @return The response entity.
     */
    protected fun <T> post(
        path: String,
        body: Any?,
        responseType: Class<T>,
    ): ResponseEntity<T> = exchange(path, HttpMethod.POST, body, responseType)

    /**
     * Performs HTTP PUT request.
     *
     * @param path The relative path.
     * @param body The request body.
     * @param responseType The response type.
     * @return The response entity.
     */
    protected fun <T> put(
        path: String,
        body: Any?,
        responseType: Class<T>,
    ): ResponseEntity<T> = exchange(path, HttpMethod.PUT, body, responseType)

    /**
     * Performs HTTP DELETE request.
     *
     * @param path The relative path.
     * @param responseType The response type.
     * @return The response entity.
     */
    protected fun <T> delete(
        path: String,
        responseType: Class<T>,
    ): ResponseEntity<T> = exchange(path, HttpMethod.DELETE, null, responseType)

    /**
     * Generic exchange method that performs HTTP request with standard headers.
     *
     * @param path The relative path.
     * @param method The HTTP method.
     * @param body The request body.
     * @param responseType The response type.
     * @return The response entity.
     */
    private fun <T> exchange(
        path: String,
        method: HttpMethod,
        body: Any?,
        responseType: Class<T>,
    ): ResponseEntity<T> {
        val url = getBaseUrl() + path

        return try {
            val headers = buildHeaders()
            val requestEntity = HttpEntity(body, headers)

            log.debug(EXECUTING_REQUEST, method, url)
            val response = restTemplate.exchange(url, method, requestEntity, responseType)
            log.debug(REQUEST_COMPLETED, response.statusCode)

            response
        } catch (e: HttpClientErrorException.NotFound) {
            log.error(RESOURCE_NOT_FOUND_LOG, url, e)
            throw ResourceNotFoundException("$RESOURCE_NOT_FOUND_MESSAGE$url")
        } catch (e: HttpClientErrorException.BadRequest) {
            log.error(BAD_REQUEST_LOG, url, e)
            throw ValidationException("$INVALID_REQUEST_MESSAGE${e.message}")
        } catch (e: HttpClientErrorException) {
            log.error(HTTP_CLIENT_ERROR_LOG, url, e)
            throw RuntimeException("$HTTP_REQUEST_FAILED_MESSAGE${e.message}", e)
        }
    }

    /**
     * Builds HTTP headers with OAuth token (if available) and trace headers from MDC.
     *
     * @return The HTTP headers.
     */
    private fun buildHeaders(): HttpHeaders = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON

        // Add OAuth token if manager is available
        oAuthTokenManager?.let {
            set(HeaderConstants.AUTHORIZATION, "$BEARER_PREFIX${it.getToken()}")
        }

        // Propagate transaction ID from MDC
        MDC.get(MdcKeys.TRANSACTION_ID)?.let {
            set(HeaderConstants.X_TRANSACTION_ID, it)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(AbstractRestClient::class.java)

        private const val EXECUTING_REQUEST = "Executing {} request to {}"
        private const val REQUEST_COMPLETED = "Request completed with status: {}"
        private const val RESOURCE_NOT_FOUND_LOG = "Resource not found: {}"
        private const val RESOURCE_NOT_FOUND_MESSAGE = "Resource not found: "
        private const val BAD_REQUEST_LOG = "Bad request: {}"
        private const val INVALID_REQUEST_MESSAGE = "Invalid request: "
        private const val HTTP_CLIENT_ERROR_LOG = "HTTP client error: {}"
        private const val HTTP_REQUEST_FAILED_MESSAGE = "HTTP request failed: "
        private const val BEARER_PREFIX = "Bearer "
    }
}
