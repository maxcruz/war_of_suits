package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import com.maxcruz.game.domain.repository.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ResolveRoundUseCaseTest {

    private val mockGameRepository: GameRepository = mockk()
    private lateinit var useCase: ResolveRoundUseCase

    @Before
    fun setUp() {
        useCase = ResolveRoundUseCase(mockGameRepository)
    }

    @Test
    fun `GIVEN a higher playing card WHEN is executed THEN should return the 1, 0`() {
        coEvery { mockGameRepository.updatePoints(any(), any()) } answers {}
        coEvery { mockGameRepository.dropCards(any()) } answers {}

        val playingCard = Card.Ace(Suit.Spades)
        val opponentCard = Card.King(Suit.Hearts)

        val result = runBlocking {
            useCase.execute("SESSION", playingCard, opponentCard)
        }

        coVerify(exactly = 0) { mockGameRepository.getGamePriority(any())  }
        result shouldBeEqualTo Pair(1,  0)
    }

    @Test
    fun `GIVEN a lower playing card WHEN is executed THEN should return the 0, 1`() {
        coEvery { mockGameRepository.updatePoints(any(), any()) } answers {}
        coEvery { mockGameRepository.dropCards(any()) } answers {}

        val playingCard = Card.Four(Suit.Diamonds)
        val opponentCard = Card.Seven(Suit.Clubs)

        val result = runBlocking {
            useCase.execute("SESSION", playingCard, opponentCard)
        }

        coVerify(exactly = 0) { mockGameRepository.getGamePriority(any())  }
        result shouldBeEqualTo Pair(0,  1)
    }

    @Test
    fun `GIVEN the same card values WHEN is executed THEN should resolve the priority`() {
        coEvery { mockGameRepository.updatePoints(any(), any()) } answers {}
        coEvery { mockGameRepository.dropCards(any()) } answers {}
        coEvery {
            mockGameRepository.getGamePriority(any())
        } returns listOf(Suit.Spades, Suit.Hearts, Suit.Clubs, Suit.Diamonds)

        val playingCard = Card.Three(Suit.Hearts)
        val opponentCard = Card.Three(Suit.Clubs)

        val result = runBlocking {
            useCase.execute("SESSION", playingCard, opponentCard)
        }

        coVerify { mockGameRepository.getGamePriority(any())  }
        result shouldBeEqualTo Pair(0,  1)
    }
}