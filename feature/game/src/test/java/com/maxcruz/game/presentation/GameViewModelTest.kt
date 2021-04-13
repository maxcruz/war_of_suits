package com.maxcruz.game.presentation

import com.maxcruz.game.TestDispatcherProvider
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Hand
import com.maxcruz.game.domain.model.Suit
import com.maxcruz.game.domain.usecase.AskOpponentCardUseCase
import com.maxcruz.game.domain.usecase.DealGameUseCase
import com.maxcruz.game.domain.usecase.EndGameUseCase
import com.maxcruz.game.domain.usecase.ResolveRoundUseCase
import com.maxcruz.game.navigation.GameNavigator
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameViewState
import com.maxcruz.game.presentation.process.GameProcessHolder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private val mockDealGameUseCase: DealGameUseCase = mockk()
    private val askOpponentUseCase: AskOpponentCardUseCase = mockk()
    private val resolveRoundUseCase: ResolveRoundUseCase = mockk()
    private val endGameUseCase: EndGameUseCase = mockk()
    private val mockNavigator: GameNavigator = mockk()
    private lateinit var viewModel: GameViewModel
    private lateinit var chanel: Channel<GameIntent>
    private lateinit var state: StateFlow<GameViewState>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val process = GameProcessHolder(
            dealGameUseCase = mockDealGameUseCase,
            askOpponentCardUseCase = askOpponentUseCase,
            resolveRoundUseCase = resolveRoundUseCase,
            endGameUseCase = endGameUseCase,
            TestDispatcherProvider
        )
        viewModel = GameViewModel(process)
        viewModel.navigator = mockNavigator
        chanel = viewModel.intents()
        state = viewModel.states()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a game load WHEN the game is deal THEN should produce the expected state`() {
        val sessionId = "SESSION"
        val player = "FOO"
        val priority = listOf(Suit.Spades, Suit.Diamonds, Suit.Hearts, Suit.Clubs)
        val deck = listOf(Card.Nine(Suit.Diamonds), Card.Queen(Suit.Clubs), Card.Three(Suit.Hearts))
        val hand = Hand(player, priority, deck, 0 to 0)
        coEvery { mockDealGameUseCase.execute(any()) } returns hand

        val state = runBlocking {
            chanel.offer(GameIntent.Load(sessionId))
            state.first()
        }

        coVerify { mockDealGameUseCase.execute(any()) }
        state.session shouldBeEqualTo sessionId
        state.player shouldBeEqualTo player
        state.isLoading shouldBeEqualTo false
        state.suitPriority shouldBeEqualTo priority.reversed()
        state.deck shouldBeEqualTo deck
    }
}