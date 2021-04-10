package com.maxcruz.game.navigation

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.maxcruz.core.domain.model.Player
import com.maxcruz.core.presentation.navigation.MVINavigator

/**
 * Navigation functions for game module
 */
class GameNavigator(navController: NavController, popUpRoute: String) : MVINavigator {

    val actionNavigateToGame: (String, Player) -> Unit = { sessionId, player ->
        var route = ROOT
        route = route.replace("{$SESSION}", sessionId)
        route = route.replace("{$USER}", player.id)
        route = route.replace("{$ROLE}", player.role.name)
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
        const val USER = "userId"
        const val ROLE = "role"

        // Route
        const val ROOT = "game_graph/{$SESSION}?user={$USER}&role={$ROLE}"
        val GAME = ROOT.replace("game_graph", "game")
    }
}