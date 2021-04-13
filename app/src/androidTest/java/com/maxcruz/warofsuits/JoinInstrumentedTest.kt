package com.maxcruz.warofsuits

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.player.presentation.join.mvi.JoinIntent
import com.maxcruz.player.presentation.join.mvi.JoinViewState
import org.amshove.kluent.shouldContain
import org.junit.Rule
import org.junit.Test

class JoinInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldSendIntentCode() {
        val intents = mutableListOf<JoinIntent>()

        composeTestRule.setContent {
            WarOfSuitsTheme {
                JoinViewState().Render{ intents.add(it) }
            }
        }

        composeTestRule.onNodeWithTag("inputTag").performTextInput("FOO")
        composeTestRule.onNodeWithTag("inputTag").performImeAction()

        intents shouldContain JoinIntent.InputCode("FOO")
    }
}