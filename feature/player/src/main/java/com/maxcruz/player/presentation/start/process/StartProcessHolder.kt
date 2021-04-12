package com.maxcruz.player.presentation.start.process

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.extensions.catchTyped
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.domain.error.PlayerException
import com.maxcruz.player.domain.usecase.FirstPlayerStartSessionUseCase
import com.maxcruz.player.presentation.start.mvi.StartIntent
import com.maxcruz.player.presentation.start.mvi.StartIntent.CreateGame
import com.maxcruz.player.presentation.start.mvi.StartIntent.JoinGame
import com.maxcruz.player.presentation.start.mvi.StartResult
import com.maxcruz.player.presentation.start.mvi.StartResult.NewGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Transform an StartIntent into a StartResult
 */
class StartProcessHolder @Inject constructor(
    private val firstPlayerStartSessionUseCase: FirstPlayerStartSessionUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : MVIProcessHolder<StartIntent, StartResult> {

    override fun processIntent(intent: StartIntent): Flow<StartResult> =
        when (intent) {
            is CreateGame -> processGameStart()
            is JoinGame -> processJoinGame()
            else -> throw UnexpectedIntentException(intent)
        }

    private fun processGameStart(): Flow<StartResult> =
        flow<NewGame> {
            val start = firstPlayerStartSessionUseCase.execute()
            emit(NewGame.WaitForSecondPlayer(start.code))
        }.onStart { emit(NewGame.Loading) }
            .catchTyped(PlayerException::class) { emit(NewGame.Failure) }
            .flowOn(dispatcherProvider.io())

    private fun processJoinGame(): Flow<StartResult> =
        flow {
            emit(NewGame.JoinToFirstPlayer)
        }
}
