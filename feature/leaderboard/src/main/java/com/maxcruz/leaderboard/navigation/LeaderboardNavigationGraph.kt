package com.maxcruz.leaderboard.navigation

import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.maxcruz.leaderboard.presentation.LeaderboardView
import com.maxcruz.leaderboard.presentation.LeaderboardViewModel

fun NavGraphBuilder.leaderboardNavigationGraph(
    parentRoute: String,
    actionNavigateUp: () -> Unit,
) {
    navigation(
        startDestination = LeaderboardRoutes.LEADERBOARD,
        route = parentRoute
    ) {
        composable(
            route = LeaderboardRoutes.LEADERBOARD
        ) { backStackEntry ->
            val viewModel = hiltNavGraphViewModel<LeaderboardViewModel>(backStackEntry)
            LeaderboardView(
                viewModel = viewModel,
                actionNavigateUp = actionNavigateUp
            )
        }
    }
}
