package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import com.maxcruz.game.domain.repository.GameRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class DealGameUseCaseTest {

    private var mockGameRepository: GameRepository = mockk()
    private lateinit var useCase: DealGameUseCase

    @Before
    fun setUp() {
        useCase = DealGameUseCase((mockGameRepository))
    }

    @Test
    fun `WHEN a new game is created THEN should generate the decks, priority and return the game hand`(){
        val sessionSlot = slot<String>()
        val prioritySlot = slot<List<Suit>>()
        val deckFirstSlot = slot<List<Card>>()
        val deckSecondSlot = slot<List<Card>>()

        every { mockGameRepository.getUserIdentifier() } returns "FOO"
        coEvery {
            mockGameRepository.createGame(
                sessionId = capture(sessionSlot),
                priority = capture(prioritySlot),
                deckFirstPlayer = capture(deckFirstSlot),
                deckSecondPlayer = capture(deckSecondSlot),
            )
        } answers {}

        val result = runBlocking {
            useCase.execute("SESSION")
        }

        coVerify { mockGameRepository.createGame(any(), any(), any(), any()) }
        sessionSlot.captured.isNotBlank() shouldBeEqualTo true
        prioritySlot.captured.size shouldBeEqualTo 4
        deckFirstSlot.captured.size shouldBeEqualTo 26
        deckSecondSlot.captured.size shouldBeEqualTo 26
        result.player shouldBeEqualTo "FOO"
        result.deck shouldBeEqualTo deckFirstSlot.captured
        result.priority shouldBeEqualTo prioritySlot.captured
        result.points shouldBeEqualTo Pair(0, 0)
    }
}