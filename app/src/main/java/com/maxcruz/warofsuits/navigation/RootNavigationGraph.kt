package com.maxcruz.warofsuits.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.navigation.compose.rememberNavController
import com.maxcruz.game.navigation.gameNavigationGraph
import com.maxcruz.leaderboard.navigation.leaderboardNavigationGraph
import com.maxcruz.player.navigation.playerNavigationGraph

@Composable
fun RootNavigationGraph(startDestination: String = RootRoutes.PLAYER) {
    val navController = rememberNavController()

    val actionNavigateToGame: (String) -> Unit = { sessionId ->
        val placeHolder = "{${RootRoutes.Arguments.SESSION}}"
        val route = RootRoutes.GAME.replace(placeHolder, sessionId)
        navController.navigate(route) {
            popUpTo(RootRoutes.PLAYER) { inclusive = true }
            launchSingleTop = true
        }
    }
    val actionNavigateToLeaderboard: () -> Unit = {
        navController.navigate(RootRoutes.LEADERBOARD)
    }
    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(navController, startDestination) {
        playerNavigationGraph(
            navController = navController,
            parentRoute = RootRoutes.PLAYER,
            actionNavigateToGame = actionNavigateToGame,
            actionNavigateToLeaderboard = actionNavigateToLeaderboard,
        )
        leaderboardNavigationGraph(
            parentRoute = RootRoutes.LEADERBOARD,
            actionNavigateUp = actionNavigateUp,
        )
        gameNavigationGraph(
            parentRoute = RootRoutes.GAME,
            actionNavigateUp = actionNavigateUp,
        )
    }
}
