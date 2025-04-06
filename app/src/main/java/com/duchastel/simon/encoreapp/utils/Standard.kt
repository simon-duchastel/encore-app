package com.duchastel.simon.encoreapp.utils

/**
 * If condition is true, calls the specified function [block] with `this` value as its receiver and
 * returns its result. Otherwise, returns this.
 */
inline fun <T> T.runIf(condition: Boolean, block: T.() -> T): T {
    return if (condition) block() else this
}

/**
 * If condition is true, calls the specified function [block] with `this` value as its receiver and
 * returns its result. Otherwise, returns this.
 */
inline fun <T> T.runIf(condition: () -> Boolean, block: T.() -> T): T {
    return if (condition()) block() else this
}