package com.maxcruz.game.domain.repository

import com.maxcruz.data.datasource.GameDataSource
import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.dto.CardDTO
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {

    private val mockPreferenceDataSource: PreferencesDataSource = mockk()
    private val mockDataSource: GameDataSource = mockk()
    private lateinit var repository: GameRepository

    @Before
    fun setUp() {
        repository = GameRepository(mockPreferenceDataSource, mockDataSource)
    }

    @Test
    fun `WHEN a new game is created THEN it should be setup the data source`() {
        val sessionSlot = slot<String>()
        val prioritySlot = slot<List<String>>()
        val deckFirstPlayerSlot = slot<List<CardDTO>>()
        val deckSecondPlayerSlot = slot<List<CardDTO>>()
        coEvery {
            mockDataSource.setupSessionGame(
                sessionId = capture(sessionSlot),
                priority = capture(prioritySlot),
                firstPlayerDeck = capture(deckFirstPlayerSlot),
                secondPlayerDeck = capture(deckSecondPlayerSlot)
            )
        } answers {}
        val sessionId = "SESSION"
        val priority = listOf(Suit.Clubs, Suit.Diamonds, Suit.Spades, Suit.Hearts)
        val firstDeck = listOf(Card.Eight(Suit.Hearts), Card.Jack(Suit.Diamonds))
        val secondDeck = listOf(Card.Ace(Suit.Spades), Card.Queen(Suit.Clubs))
        runBlocking {
            repository.createGame(sessionId, priority,firstDeck, secondDeck)
        }

        coVerify { mockDataSource.setupSessionGame(any(), any(), any(), any()) }
        sessionSlot.captured shouldBeEqualTo sessionId
        prioritySlot.captured shouldBeEqualTo priority.map { it.name }
        val resultFirstDeck = deckFirstPlayerSlot.captured
        for (i in 0 until 2) {
            resultFirstDeck[i].value shouldBeEqualTo firstDeck[i].value
            resultFirstDeck[i].suit shouldBeEqualTo firstDeck[i].suit.name
        }
    }

}