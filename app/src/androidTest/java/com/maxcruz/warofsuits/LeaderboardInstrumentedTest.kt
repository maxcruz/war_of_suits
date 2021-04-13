package com.maxcruz.warofsuits

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.leaderboard.navigation.LeaderboardNavigator
import com.maxcruz.leaderboard.presentation.LeaderboardView
import com.maxcruz.leaderboard.presentation.LeaderboardViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class LeaderboardInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun leaderboardScreenShouldNavigate() {
        val navigator: LeaderboardNavigator = mockk()
        val viewModel: LeaderboardViewModel = mockk()
        every { viewModel.navigator } returns navigator
        every { navigator.actionNavigateUp() } answers {}

        composeTestRule.setContent {
            WarOfSuitsTheme {
                LeaderboardView(viewModel = viewModel)
            }
        }

        composeTestRule.onRoot().printToLog("TAG")
        composeTestRule.onNode(hasContentDescription("Close"), useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Coming! \uD83D\uDC77").assertIsDisplayed()
        verify { navigator.actionNavigateUp() }
    }
}