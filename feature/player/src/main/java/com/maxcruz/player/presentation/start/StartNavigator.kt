package com.maxcruz.player.presentation.start

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.maxcruz.core.navigation.Navigator
import com.maxcruz.player.navigation.PlayerRoutes

class StartNavigator(
    navController: NavController,
    val actionNavigateToGame: (String) -> Unit,
    val actionNavigateToLeaderboard: () -> Unit,
) : Navigator {

    val actionNavigateToWaiting: (String) -> Unit = { player1 ->
        val placeHolder = "{${PlayerRoutes.Arguments.PLAYER_1}}"
        val route = PlayerRoutes.WAITING.replace(placeHolder, player1)
        navController.navigate(route)
    }

    val actionNavigateToJoin: (String) -> Unit = { player2 ->
        val placeHolder = "{${PlayerRoutes.Arguments.PLAYER_2}}"
        val route = PlayerRoutes.JOIN.replace(placeHolder, player2)
        navController.navigate(route)
    }
}