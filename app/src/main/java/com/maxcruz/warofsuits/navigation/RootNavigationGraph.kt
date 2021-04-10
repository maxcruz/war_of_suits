package com.maxcruz.warofsuits.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maxcruz.game.navigation.GameNavigator
import com.maxcruz.game.navigation.gameNavigationGraph
import com.maxcruz.leaderboard.navigation.LeaderboardNavigator
import com.maxcruz.leaderboard.navigation.leaderboardNavigationGraph
import com.maxcruz.player.navigation.PlayerNavigator
import com.maxcruz.player.navigation.playerNavigationGraph

@Composable
fun RootNavigationGraph(startDestination: String = PlayerNavigator.ROOT) {
    val navController = rememberNavController()

    val gameNavigator = GameNavigator(
        navController = navController,
        popUpRoute = startDestination,
    )
    val leaderboardNavigator = LeaderboardNavigator(
        navController = navController,
    )
    val playerNavigator = PlayerNavigator(
        navController = navController,
        actionNavigateToGame = gameNavigator::actionNavigateToGame.get(),
        actionNavigateToLeaderboard = leaderboardNavigator::actionNavigateToLeaderboard.get(),
    )

    NavHost(navController, startDestination) {
        playerNavigationGraph(playerNavigator)
        leaderboardNavigationGraph(leaderboardNavigator)
        gameNavigationGraph(gameNavigator)
    }
}
