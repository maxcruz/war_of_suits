package com.maxcruz.leaderboard.navigation

import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.maxcruz.leaderboard.navigation.LeaderboardNavigator.Companion.LEADERBOARD
import com.maxcruz.leaderboard.navigation.LeaderboardNavigator.Companion.ROOT
import com.maxcruz.leaderboard.presentation.LeaderboardView
import com.maxcruz.leaderboard.presentation.LeaderboardViewModel

fun NavGraphBuilder.leaderboardNavigationGraph(leaderboardNavigator: LeaderboardNavigator) {
    navigation(startDestination = LEADERBOARD, route = ROOT) {
        composable(route = LEADERBOARD) { backStackEntry ->
            val viewModel = hiltNavGraphViewModel<LeaderboardViewModel>(backStackEntry)
            viewModel.navigator = leaderboardNavigator
            LeaderboardView(viewModel = viewModel)
        }
    }
}
