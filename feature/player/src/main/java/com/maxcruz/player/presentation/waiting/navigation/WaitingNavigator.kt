package com.maxcruz.player.presentation.waiting.navigation

import com.maxcruz.core.presentation.navigation.MVINavigator

class WaitingNavigator(
    val actionNavigateToGame: (String) -> Unit,
    val actionNavigateUp: () -> Unit,
) : MVINavigator