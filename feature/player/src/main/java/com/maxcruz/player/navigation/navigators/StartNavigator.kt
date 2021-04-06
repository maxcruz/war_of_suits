package com.maxcruz.player.navigation.navigators

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.maxcruz.core.presentation.navigation.MVINavigator
import com.maxcruz.player.navigation.PlayerRoutes

class StartNavigator(
    navController: NavController,
    val actionNavigateToGame: (String) -> Unit,
    val actionNavigateToLeaderboard: () -> Unit,
) : MVINavigator {

    val actionNavigateToWaiting: (String) -> Unit = { player1 ->
        val placeHolder = "{${PlayerRoutes.Arguments.CODE}}"
        val route = PlayerRoutes.WAITING.replace(placeHolder, player1)
        navController.navigate(route)
    }

    val actionNavigateToJoin: () -> Unit = {
        navController.navigate(PlayerRoutes.JOIN)
    }
}