package com.maxcruz.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlin.reflect.KClass

/**
 * Limit exception handling to the expected errors
 */
fun <T, E : Throwable> Flow<T>.catchTyped(
    type: KClass<E>,
    action: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
): Flow<T> {
    return this.catch {
        if (type.isInstance(it)) {
            action(it)
        } else {
            throw IllegalStateException("Propagating error due to fail fast policy", it)
        }
    }
}