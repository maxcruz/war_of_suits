package com.maxcruz.core.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

/**
 * This component offers a publisher to receive intents from the view and a subscription
 * to emit immutable view states
 */
interface MVIViewModel<I : MVIIntent, S : MVIViewState<*, *>> {
    fun intents(): Channel<I>
    fun states(): Flow<S>
}