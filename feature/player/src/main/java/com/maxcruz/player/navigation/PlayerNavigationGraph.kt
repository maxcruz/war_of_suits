package com.maxcruz.player.navigation

import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navigation
import com.maxcruz.player.navigation.PlayerNavigator.Companion.CODE
import com.maxcruz.player.navigation.PlayerNavigator.Companion.JOIN
import com.maxcruz.player.navigation.PlayerNavigator.Companion.ROOT
import com.maxcruz.player.navigation.PlayerNavigator.Companion.START
import com.maxcruz.player.navigation.PlayerNavigator.Companion.WAITING
import com.maxcruz.player.presentation.join.JoinView
import com.maxcruz.player.presentation.join.JoinViewModel
import com.maxcruz.player.presentation.start.StartView
import com.maxcruz.player.presentation.start.StartViewModel
import com.maxcruz.player.presentation.waiting.WaitingView
import com.maxcruz.player.presentation.waiting.WaitingViewModel

/**
 * Player module navigation graph
 */
fun NavGraphBuilder.playerNavigationGraph(playerNavigator: PlayerNavigator) {
    navigation(startDestination = START, route = ROOT) {
        composable(route = START) { backStackEntry ->
            val startViewModel = hiltNavGraphViewModel<StartViewModel>(backStackEntry)
            startViewModel.navigator = playerNavigator
            StartView(viewModel = startViewModel)
        }
        val arguments = listOf(navArgument(CODE) { type = NavType.StringType })
        composable(route = WAITING, arguments = arguments) { backStackEntry ->
            val waitingViewModel = hiltNavGraphViewModel<WaitingViewModel>(backStackEntry)
            val code = requireNotNull(backStackEntry.arguments?.getString(CODE))
            waitingViewModel.navigator = playerNavigator
            WaitingView(viewModel = waitingViewModel, code = code)
        }
        composable(route = JOIN) { backStackEntry ->
            val joinViewModel = hiltNavGraphViewModel<JoinViewModel>(backStackEntry)
            joinViewModel.navigator = playerNavigator
            JoinView(viewModel = joinViewModel)
        }
    }
}
