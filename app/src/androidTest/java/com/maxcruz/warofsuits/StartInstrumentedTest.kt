package com.maxcruz.warofsuits

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.player.presentation.start.mvi.StartIntent
import com.maxcruz.player.presentation.start.mvi.StartViewState
import org.amshove.kluent.shouldContain
import org.junit.Rule
import org.junit.Test

class StartInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun startShouldTriggerActionsWithButtons() {
        val actions = mutableListOf<StartIntent>()

        composeTestRule.setContent {
            WarOfSuitsTheme {
                StartViewState().Render{ actions.add(it) }
            }
        }

        composeTestRule.onNodeWithText("Start a new game").performClick()
        composeTestRule.onNodeWithText("Join to a game").performClick()
        composeTestRule.onNodeWithText("Leaderboard").performClick()
        actions shouldContain StartIntent.CreateGame
        actions shouldContain StartIntent.JoinGame
        actions shouldContain StartIntent.RouteToLeaderboard
    }

    @Test
    fun buttonsShouldBeDisabledOnLoading() {
        composeTestRule.setContent {
            WarOfSuitsTheme {
                StartViewState(isLoading = true).Render{}
            }
        }
        composeTestRule.onNodeWithText("Start a new game").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Join to a game").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Leaderboard").assertIsNotEnabled()
    }
}