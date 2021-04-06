package com.maxcruz.player.presentation.start.process

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.extensions.catchTyped
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.domain.error.PlayerException
import com.maxcruz.player.domain.usecase.FirstPlayerStartGameUseCase
import com.maxcruz.player.domain.usecase.FirstPlayerStartGameUseCase.StartGame
import com.maxcruz.player.domain.usecase.RecoverGameUseCase
import com.maxcruz.player.presentation.start.mvi.StartIntent
import com.maxcruz.player.presentation.start.mvi.StartIntent.*
import com.maxcruz.player.presentation.start.mvi.StartResult
import com.maxcruz.player.presentation.start.mvi.StartResult.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Transform an StartIntent into a StartResult
 */
class StartProcessHolder @Inject constructor(
    private val firstPlayerStartGameUseCase: FirstPlayerStartGameUseCase,
    private val recoverGameUseCase: RecoverGameUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : MVIProcessHolder<StartIntent, StartResult> {

    override fun processIntent(intent: StartIntent): Flow<StartResult> {
        return when (intent) {
            CreateGame -> processGameStart()
            JoinGame -> processJoinGame()
            RecoverGame -> processRecoverGame()
            RouteToLeaderboard -> processRouteToLeaderboard()
        }
    }

    private fun processGameStart(): Flow<StartResult> =
        flow {
            when (val start = firstPlayerStartGameUseCase.execute()) {
                is StartGame.GameStarted -> {
                    emit(RecoverGameAttempt.GameSessionFound(start.sessionId))
                }
                is StartGame.JoinSecondPlayer -> {
                    emit(NewGame.WaitForSecondPlayer(start.code))
                }
            }
        }.onStart { emit(NewGame.Loading) }
            .catchTyped(PlayerException::class) { emit(NewGame.Failure) }
            .flowOn(dispatcherProvider.io())

    private fun processJoinGame(): Flow<StartResult> =
        flow {
            emit(NewGame.JoinToFirstPlayer)
        }

    private fun processRecoverGame(): Flow<RecoverGameAttempt> =
        flow {
            val sessionId = recoverGameUseCase.execute()
            sessionId?.let { emit(RecoverGameAttempt.GameSessionFound(it)) }
                ?: emit(RecoverGameAttempt.NoGameAvailable)
        }.onStart { emit(RecoverGameAttempt.Loading) }
            .catchTyped(PlayerException::class) {
                emit(RecoverGameAttempt.NoGameAvailable)
            }
            .flowOn(dispatcherProvider.io())

    private fun processRouteToLeaderboard(): Flow<NavigateToLeaderboard> =
        flow {
            emit(NavigateToLeaderboard)
        }
}
