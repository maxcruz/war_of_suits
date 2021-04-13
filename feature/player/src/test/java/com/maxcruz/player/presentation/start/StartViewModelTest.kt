package com.maxcruz.player.presentation.start

import com.maxcruz.player.TestDispatcherProvider
import com.maxcruz.player.domain.usecase.FirstPlayerStartSessionUseCase
import com.maxcruz.player.domain.usecase.FirstPlayerStartSessionUseCase.JoinSecondPlayer
import com.maxcruz.player.navigation.PlayerNavigator
import com.maxcruz.player.presentation.start.mvi.StartIntent
import com.maxcruz.player.presentation.start.mvi.StartViewState
import com.maxcruz.player.presentation.start.process.StartProcessHolder
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class StartViewModelTest {

    private var mockFirstPlayerStartSessionUseCase: FirstPlayerStartSessionUseCase = mockk()
    private var mockNavigator: PlayerNavigator = mockk()

    private lateinit var viewModel: StartViewModel
    private lateinit var chanel: Channel<StartIntent>
    private lateinit var state: StateFlow<StartViewState>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val process = StartProcessHolder(mockFirstPlayerStartSessionUseCase, TestDispatcherProvider)
        viewModel = StartViewModel(process)
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
    fun `WHEN the game creation intent is received THEN should start the session and navigate to waiting`() {
        val code = "FOO"
        coEvery { mockFirstPlayerStartSessionUseCase.execute() } returns JoinSecondPlayer(code)
        every { mockNavigator.actionNavigateToWaiting(any()) } answers {}

        runBlocking {
            chanel.offer(StartIntent.CreateGame)
            state.first()
        }

        verify { mockNavigator.actionNavigateToWaiting(code) }
        coVerify { mockFirstPlayerStartSessionUseCase.execute() }
    }

}