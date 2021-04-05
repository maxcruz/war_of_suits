package com.maxcruz.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxcruz.core.navigation.Navigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

/**
 * This component offers a publisher to receive intents from the view and a subscription
 * to emit immutable view states
 */
abstract class MVIViewModel<I : MVIIntent, S : MVIViewState<I>, R : MVIResult, N : Navigator>(
    initialState: S,
    initialIntent: I? = null,
) : ViewModel() {

    abstract var navigator: N

    private val intentChannel: Channel<I> = Channel(Channel.UNLIMITED)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val stateChannel: StateFlow<S> = intentChannel
        .receiveAsFlow()
        .onStart { initialIntent?.let { emit(it) } }
        .flatMapMerge(transform = ::transformer)
        .scan(initialState, ::reducer)
        .stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    /**
     * Stream events from the view
     */
    fun intents(): Channel<I> = intentChannel

    /**
     * Receive states from the view
     */
    fun states(): StateFlow<S> = stateChannel

    /**
     * Processor to transform an intent into a flow of results
     */
    protected abstract suspend fun transformer(intent: I): Flow<R>

    /**
     * Receives an state with an operation result to generate a new state
     */
    protected abstract suspend fun reducer(previous: S, result: R): S
}