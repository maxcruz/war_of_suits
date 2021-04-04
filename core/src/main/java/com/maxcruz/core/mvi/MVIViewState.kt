package com.maxcruz.core.mvi

import androidx.compose.runtime.Composable

/**
 * This interface should be implemented by a data class representing the states of the UI
 */
interface MVIViewState<I : MVIIntent, N : MVINavigation> {

    var navigationQueue: N?

    /**
     * Retrieve any pending navigation in the state and clear it after
     */
    fun dequeueNavigation(): N? = navigationQueue?.also { navigationQueue = null }

    /**
     * Composable method to render the UI state and receive intent actions from the user
     */
    @Composable
    fun Render(action: (I) -> Unit = {})
}
