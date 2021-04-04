package com.maxcruz.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provide coroutine dispatchers by DI to improve testability
 */
interface DispatcherProvider {

    /**
     * Expensive background tasks
     * Level of parallelism equal to the number of CPU cores (is at least two)
     */
    fun default(): CoroutineDispatcher

    /**
     * IO tasks like network calls
     * Maximum level of parallelism, limited by kotlinx.coroutines.io.parallelism property
     */
    fun io(): CoroutineDispatcher

    /**
     * UI tasks
     * Tasks confined to the Android main thread operating with UI objects
     */
    fun main(): CoroutineDispatcher
}