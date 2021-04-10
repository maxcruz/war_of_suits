package com.maxcruz.player.navigation

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.maxcruz.core.domain.model.Player
import com.maxcruz.core.presentation.navigation.MVINavigator

class PlayerNavigator(
    navController: NavController,
    val actionNavigateToGame: (String, Player) -> Unit,
    val actionNavigateToLeaderboard: () -> Unit,
) : MVINavigator {

    val actionNavigateToWaiting: (String) -> Unit = { player1 ->
        val route = WAITING.replace("{$CODE}", player1)
        navController.navigate(route)
    }

    val actionNavigateToJoin: () -> Unit = {
        navController.navigate(JOIN)
    }

    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    companion object {
        // Arguments
        const val CODE = "code"

        // Routes
        const val ROOT = "player_graph"
        const val START = "start"
        const val WAITING = "waiting/{$CODE}"
        const val JOIN = "join"
    }
}