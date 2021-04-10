package com.maxcruz.leaderboard.navigation

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.maxcruz.core.presentation.navigation.MVINavigator

class LeaderboardNavigator(navController: NavController) : MVINavigator {

    val actionNavigateToLeaderboard: () -> Unit = {
        navController.navigate(ROOT)
    }

    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    companion object {
        // Route
        const val ROOT = "top_players_graph"
        const val LEADERBOARD = "top_players"
    }
}
