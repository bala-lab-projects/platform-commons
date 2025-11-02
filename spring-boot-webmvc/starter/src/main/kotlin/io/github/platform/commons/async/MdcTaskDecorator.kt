package io.github.platform.commons.async

import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator
import org.springframework.stereotype.Component

/**
 * Task decorator that propagates MDC context to async threads.
 * Ensures that logging context is preserved when using @Async methods.
 */
@Component
class MdcTaskDecorator : TaskDecorator {
    /**
     * Decorates the given Runnable to copy MDC context from the parent thread to the async thread.
     *
     * @param runnable The Runnable to decorate.
     * @return The decorated Runnable.
     */
    override fun decorate(runnable: Runnable): Runnable {
        // Capture MDC context from the current thread
        val contextMap = MDC.getCopyOfContextMap()

        return Runnable {
            try {
                // Set MDC context in the async thread
                contextMap?.let { MDC.setContextMap(it) }
                // Execute the original runnable
                runnable.run()
            } finally {
                // Clear MDC context to prevent memory leaks
                MDC.clear()
            }
        }
    }
}
