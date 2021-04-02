package com.maxcruz.core.mvi

import androidx.compose.runtime.Composable

/**
 * This interface should be implemented by a data class representing the states of the UI
 */
interface MviViewState {

    /**
     * Compose method to render the UI state
     */
    @Composable
    fun Render(action: (MviAction) -> Unit = {})

}