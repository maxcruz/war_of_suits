package com.maxcruz.core.presentation.process

import com.maxcruz.core.presentation.mvi.MVIIntent
import com.maxcruz.core.presentation.mvi.MVIResult
import kotlinx.coroutines.flow.Flow

/**
 * Keep intents processing in a single place
 */
interface MVIProcessHolder<I : MVIIntent, R : MVIResult> {

    /**
     * Transform an intent into a stream of results
     */
    fun processIntent(intent: I): Flow<R>
}