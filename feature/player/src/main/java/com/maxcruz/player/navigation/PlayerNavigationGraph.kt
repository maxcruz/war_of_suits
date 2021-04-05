package com.maxcruz.player.navigation

import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigation
import com.maxcruz.player.presentation.join.JoinView
import com.maxcruz.player.presentation.join.JoinViewModel
import com.maxcruz.player.presentation.start.StartNavigator
import com.maxcruz.player.presentation.start.StartView
import com.maxcruz.player.presentation.start.StartViewModel
import com.maxcruz.player.presentation.waiting.WaitingView
import com.maxcruz.player.presentation.waiting.WaitingViewModel
import com.maxcruz.player.presentation.waiting.navigation.WaitingNavigator

/**
 * Player module navigation graph
 */
fun NavGraphBuilder.playerNavigationGraph(
    navController: NavController,
    parentRoute: String,
    actionNavigateToGame: (String) -> Unit,
    actionNavigateToLeaderboard: () -> Unit,
) {

    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    navigation(startDestination = PlayerRoutes.START, route = parentRoute) {
        composable(
            route = PlayerRoutes.START,
        ) { backStackEntry ->
            val startViewModel = hiltNavGraphViewModel<StartViewModel>(backStackEntry)
            val startNavigator = StartNavigator(
                navController = navController,
                actionNavigateToGame = actionNavigateToGame,
                actionNavigateToLeaderboard = actionNavigateToLeaderboard
            )
            startViewModel.navigator = startNavigator
            StartView(viewModel = startViewModel)
        }
        composable(
            route = PlayerRoutes.WAITING,
            arguments = listOf(
                navArgument(PlayerRoutes.Arguments.PLAYER_1) {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            val waitingViewModel = hiltNavGraphViewModel<WaitingViewModel>(backStackEntry)
            val waitingNavigator = WaitingNavigator(
                actionNavigateToGame = actionNavigateToGame,
                actionNavigateUp = actionNavigateUp,
            )
            waitingViewModel.navigator = waitingNavigator
            val argument = PlayerRoutes.Arguments.PLAYER_1
            val player1 = requireNotNull(backStackEntry.arguments?.getString(argument))
            WaitingView(
                viewModel = waitingViewModel,
                player1 = player1,
            )
        }
        composable(
            route = PlayerRoutes.JOIN,
            arguments = listOf(
                navArgument(PlayerRoutes.Arguments.PLAYER_2) {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            val joinViewModel = hiltNavGraphViewModel<JoinViewModel>(backStackEntry)
            val argument = PlayerRoutes.Arguments.PLAYER_2
            val player2 = requireNotNull(backStackEntry.arguments?.getString(argument))
            JoinView(
                viewModel = joinViewModel,
                player2 = player2,
                actionNavigateToGame = actionNavigateToGame,
                actionNavigateUp = actionNavigateUp
            )
        }
    }
}
