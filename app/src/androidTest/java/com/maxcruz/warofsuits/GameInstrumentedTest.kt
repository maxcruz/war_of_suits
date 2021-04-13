package com.maxcruz.warofsuits

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameViewState
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Rule
import org.junit.Test

class GameInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun playerShouldBeDisplayed() {
        composeTestRule.setContent {
            WarOfSuitsTheme {
                GameViewState(player = "ID007").Render{ }
            }
        }
        composeTestRule.onNode(hasText("ID007", substring = true)).assertIsDisplayed()
    }

    @Test
    fun shouldShowThePriority() {
        val priority = mutableListOf(Suit.Diamonds, Suit.Spades, Suit.Hearts, Suit.Clubs)

        composeTestRule.setContent {
            WarOfSuitsTheme {
                GameViewState(suitPriority = priority).Render{ }
            }
        }

        val nodes = composeTestRule.onAllNodes(hasTestTag("priority"))
        nodes[0].assertContentDescriptionContains(Suit.Diamonds.name)
        nodes[1].assertContentDescriptionContains(Suit.Spades.name)
        nodes[2].assertContentDescriptionContains(Suit.Hearts.name)
        nodes[3].assertContentDescriptionContains(Suit.Clubs.name)
    }

    @Test
    fun shouldSendCardOnSwipeAction() {
        val viewState = GameViewState(
            session = "foo",
            deck = listOf(Card.Two(Suit.Clubs), Card.Ace(Suit.Spades)),
            discard = listOf(),
        )
        val intents = mutableListOf<GameIntent>()

        composeTestRule.setContent {
            WarOfSuitsTheme {
                viewState.Render{ intents.add(it) }
            }
        }

        composeTestRule.onNode(hasTestTag("1 clubs"), useUnmergedTree = true)
            .assertIsDisplayed()
            .performGesture {
                swipe(start = centerLeft, end = Offset(x = 1000f, y= centerY), durationMillis = 200)
            }
            .performClick()

        val result = intents.firstOrNull() ?: error("Intent was expected")
        result shouldBeInstanceOf GameIntent.PlayCard::class
        result as GameIntent.PlayCard
        result.card.value shouldBeEqualTo Card.Two(Suit.Clubs).value
        result.card.suit shouldBeEqualTo Card.Two(Suit.Clubs).suit
    }
}