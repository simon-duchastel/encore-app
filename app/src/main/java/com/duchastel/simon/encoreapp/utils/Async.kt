package com.duchastel.simon.encoreapp.utils

/**
 * The T generic is unused for some classes but since it is sealed and useful for Success and Fail,
 * it should be on all of them.
 *
 * Complete: Success, Fail
 * ShouldLoad: Uninitialized, Fail
 *
 * Taken from the Airbnb Mavericks library: https://github.com/airbnb/mavericks
 */
sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean, private val value: T?) {

    /**
     * Returns the value or null.
     *
     * Success always have a value. Loading and Fail can also return a value which is useful for
     * pagination or progressive data loading.
     *
     * Can be invoked as an operator like: `yourProp()`
     */
    open operator fun invoke(): T? = value


    // Types

    object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true, value = null),
        Incomplete

    data class Loading<out T>(private val value: T? = null) :
        Async<T>(complete = false, shouldLoad = false, value = value), Incomplete

    data class Success<out T>(private val value: T) :
        Async<T>(complete = true, shouldLoad = false, value = value) {

        override operator fun invoke(): T = value

        /**
         * Optional information about the value.
         * This is intended to support tooling (eg logging).
         * It allows data about the original Observable to be kept and accessed later. For example,
         * you could map a network request to just the data you need in the value, but your base layers could
         * keep metadata about the request, like timing, for logging.
         *
         * @see Async.setMetadata
         * @see Async.getMetadata
         */
        var metadata: Any? = null
    }

    data class Fail<out T>(val error: Throwable, private val value: T? = null) :
        Async<T>(complete = true, shouldLoad = true, value = value) {
        override fun equals(other: Any?): Boolean {
            if (other !is Fail<*>) return false

            val otherError = other.error
            return error::class == otherError::class &&
                    error.message == otherError.message &&
                    error.stackTrace.firstOrNull() == otherError.stackTrace.firstOrNull()
        }

        override fun hashCode(): Int =
            arrayOf(error::class, error.message, error.stackTrace.firstOrNull()).contentHashCode()
    }

    /**
     * Helper interface for using Async in a when clause for handling both Uninitialized and Loading.
     *
     * With this, you can do:
     * when (data) {
     *     is Incomplete -> Unit
     *     is Success    -> Unit
     *     is Fail       -> Unit
     * }
     */
    interface Incomplete

    companion object {
        /**
         * Helper to set metadata on a Success instance.
         * @see Success.metadata
         */
        fun <T> Success<*>.setMetadata(metadata: T) {
            this.metadata = metadata
        }

        /**
         * Helper to get metadata on a Success instance.
         * @see Success.metadata
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> Success<*>.getMetadata(): T? = this.metadata as T?
    }
}