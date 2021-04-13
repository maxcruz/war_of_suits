package com.maxcruz.player.presentation.waiting

import com.maxcruz.player.TestDispatcherProvider
import com.maxcruz.player.domain.usecase.WaitSecondPlayerUseCase
import com.maxcruz.player.navigation.PlayerNavigator
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent
import com.maxcruz.player.presentation.waiting.mvi.WaitingViewState
import com.maxcruz.player.presentation.waiting.process.WaitingProcessHolder
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test

class WaitingViewModelTest {

    private var mockWaitSecondPlayerUseCase: WaitSecondPlayerUseCase = mockk()
    private var mockNavigator: PlayerNavigator = mockk()

    private lateinit var viewModel: WaitingViewModel
    private lateinit var chanel: Channel<WaitingIntent>
    private lateinit var state: StateFlow<WaitingViewState>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val process = WaitingProcessHolder(mockWaitSecondPlayerUseCase, TestDispatcherProvider)
        viewModel = WaitingViewModel(process)
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
    fun `GIVEN a user waiting WHEN the second player joins THEN should navigate to game`() {
        val slot = slot<String>()
        val secondPLayerChannel: Flow<String> = flow { emit("SESSION") }
        every { mockNavigator.actionNavigateToGame(capture(slot)) } answers {}
        coEvery { mockWaitSecondPlayerUseCase.execute(any()) } returns secondPLayerChannel
        val state = runBlocking {
            chanel.offer(WaitingIntent.Load("FOO"))
            state.first()
        }

        verify { mockNavigator.actionNavigateToGame(slot.captured) }
        state shouldBeEqualTo WaitingViewState("FOO")
    }

}