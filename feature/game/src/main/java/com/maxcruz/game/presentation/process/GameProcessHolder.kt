package com.maxcruz.game.presentation.process

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.game.domain.usecase.DealGameUseCase
import com.maxcruz.game.domain.usecase.WaitCardsUseCase
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameIntent.Close
import com.maxcruz.game.presentation.mvi.GameIntent.Load
import com.maxcruz.game.presentation.mvi.GameResult
import com.maxcruz.game.presentation.mvi.GameResult.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GameProcessHolder @Inject constructor(
    private val dealGameUseCase: DealGameUseCase,
    private val waitCardsUseCase: WaitCardsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : MVIProcessHolder<GameIntent, GameResult> {

    override fun processIntent(intent: GameIntent): Flow<GameResult> =
        when (intent) {
            is Load -> processGameLoad(intent)
            is Close -> processClose()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun processGameLoad(intent: Load): Flow<GameLoad> =
        flow {
            emit(GameLoad.Loading)
            val (sessionId, dealer) = intent
            if (dealer) {
                val hand = dealGameUseCase.execute(sessionId)
                emit(GameLoad.GameReady(hand))
            } else {
                // Logic to wait for the event
                val hand = waitCardsUseCase.execute(sessionId)
                merge(hand)
            }
        }

    private fun processClose(): Flow<GameResult> = flow {
        // Call usecase to inform the end of the game
        emit(Closed)
    }
}