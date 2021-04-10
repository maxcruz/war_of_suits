package com.maxcruz.game.navigation

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.maxcruz.core.presentation.navigation.MVINavigator

/**
 * Navigation functions for game module
 */
class GameNavigator(navController: NavController, popUpRoute: String) : MVINavigator {

    val actionNavigateToGame: (String, Boolean) -> Unit = { sessionId, dealer ->
        var route = ROOT
        route = route.replace("{$SESSION}", sessionId)
        route = route.replace("{$DEALER}", dealer.toString())
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
        const val DEALER = "dealer"

        // Route
        const val ROOT = "game_graph/{$SESSION}/{$DEALER}"
        val GAME = ROOT.replace("game_graph", "game")
    }
}