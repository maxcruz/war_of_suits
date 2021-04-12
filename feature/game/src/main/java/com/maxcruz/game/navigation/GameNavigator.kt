package com.maxcruz.game.navigation

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.maxcruz.core.presentation.navigation.MVINavigator

/**
 * Navigation functions for game module
 */
class GameNavigator(navController: NavController, popUpRoute: String) : MVINavigator {

    val actionNavigateToGame: (String) -> Unit = { sessionId ->
        var route = ROOT
        route = route.replace("{$SESSION}", sessionId)
        navController.navigate(route) {
            popUpTo(popUpRoute) { inclusive = true }
            launchSingleTop = true
        }
    }

    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    companion object {
        // Arguments
        const val SESSION = "sessionId"

        // Route
        const val ROOT = "game_graph/{$SESSION}"
        val GAME = ROOT.replace("game_graph", "game")
    }
}