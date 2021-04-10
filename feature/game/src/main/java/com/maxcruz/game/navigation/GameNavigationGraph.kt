package com.maxcruz.game.navigation

import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigation
import com.maxcruz.game.navigation.GameNavigator.Companion.DEALER
import com.maxcruz.game.navigation.GameNavigator.Companion.GAME
import com.maxcruz.game.navigation.GameNavigator.Companion.ROOT
import com.maxcruz.game.navigation.GameNavigator.Companion.SESSION
import com.maxcruz.game.presentation.GameView
import com.maxcruz.game.presentation.GameViewModel

fun NavGraphBuilder.gameNavigationGraph(gameNavigator: GameNavigator) {
    navigation(startDestination = GAME, route = ROOT) {
        val arguments = listOf(
            navArgument(SESSION) { type = NavType.StringType },
            navArgument(DEALER) { type = NavType.StringType },
        )
        composable(route = GAME, arguments = arguments) { backStackEntry ->
            val viewModel = hiltNavGraphViewModel<GameViewModel>(backStackEntry)
            viewModel.navigator = gameNavigator
            val sessionId = requireNotNull(backStackEntry.arguments?.getString(SESSION))
            val dealer = requireNotNull(backStackEntry.arguments?.getString(DEALER))
            GameView(
                viewModel = viewModel,
                sessionId = sessionId,
                dealer = dealer.toBoolean()
            )
        }
    }
}
