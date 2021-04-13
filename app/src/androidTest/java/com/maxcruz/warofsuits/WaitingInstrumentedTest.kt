package com.maxcruz.warofsuits

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.player.presentation.waiting.mvi.WaitingViewState
import org.junit.Rule
import org.junit.Test

class WaitingInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun waitingViewShouldShowACode() {
        composeTestRule.setContent {
            WarOfSuitsTheme {
                WaitingViewState(code = "FOO").Render{}
            }
        }

        composeTestRule.onNode(hasText("FOO", substring = true)).assertIsDisplayed()
    }
}