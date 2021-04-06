package com.maxcruz.player.navigation.navigators

import com.maxcruz.core.presentation.navigation.MVINavigator

class GameStartNavigator(
    val actionNavigateToGame: (String) -> Unit,
    val actionNavigateUp: () -> Unit,
) : MVINavigator