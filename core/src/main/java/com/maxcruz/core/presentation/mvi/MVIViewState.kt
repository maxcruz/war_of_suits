package com.maxcruz.core.presentation.mvi

import androidx.compose.runtime.Composable

/**
 * This interface should be implemented by a data class representing the states of the UI
 */
interface MVIViewState<I : MVIIntent> {

    /**
     * Composable method to render the UI state and receive intent actions from the user
     */
    @Composable
    fun Render(action: (I) -> Unit = {})
}
