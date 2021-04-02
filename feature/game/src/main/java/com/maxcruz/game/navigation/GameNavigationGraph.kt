package com.maxcruz.game.navigation

import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigation
import com.maxcruz.game.presentation.GameView
import com.maxcruz.game.presentation.GameViewModel

fun NavGraphBuilder.gameNavigationGraph(
    parentRoute: String,
    actionNavigateUp: () -> Unit,
) {
    navigation(
        startDestination = GameRoutes.GAME,
        route = parentRoute,
    ) {
        composable(
            route = GameRoutes.GAME,
            arguments = listOf(
                navArgument(GameRoutes.Arguments.SESSION) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val viewModel = hiltNavGraphViewModel<GameViewModel>(backStackEntry)
            val argument = GameRoutes.Arguments.SESSION
            val sessionId = requireNotNull(backStackEntry.arguments?.getString(argument))
            GameView(
                viewModel = viewModel,
                sessionId = sessionId,
                actionNavigateUp = actionNavigateUp
            )
        }
    }
}
