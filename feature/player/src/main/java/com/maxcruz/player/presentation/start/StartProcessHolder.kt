package com.maxcruz.player.presentation.start

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.extensions.catchTyped
import com.maxcruz.player.domain.error.PlayerException
import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.usecase.FirstPlayerStartGameUseCase
import com.maxcruz.player.domain.usecase.GetUserUseCase
import com.maxcruz.player.domain.usecase.RecoverGameUseCase
import com.maxcruz.player.presentation.start.StartIntent.*
import com.maxcruz.player.presentation.start.StartProcessHolder.NewGameOption.Join
import com.maxcruz.player.presentation.start.StartProcessHolder.NewGameOption.Start
import com.maxcruz.player.presentation.start.StartResult.*
import com.maxcruz.player.presentation.start.StartResult.RecoverGameAttempt.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Transform an StartIntent into a StartResult
 */
class StartProcessHolder @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val firstPlayerStartGameUseCase: FirstPlayerStartGameUseCase,
    private val recoverGameUseCase: RecoverGameUseCase,
    private val dispatcherProvider: DispatcherProvider,
) {

    fun processIntent(intent: StartIntent): Flow<StartResult> {
        return when (intent) {
            CreateGame -> processGameStart(Start)
            JoinGame -> processGameStart(Join)
            RecoverGame -> processRecoverGame()
            RouteToLeaderboard -> processRouteToLeaderboard()
        }
    }

    private fun processGameStart(option: NewGameOption): Flow<NewGame> =
        flow<NewGame> {
            val userId = getUserUseCase.execute()
            val player: Player = when (option) {
                Join -> {
                    Player.SecondPlayer(userId)
                }
                Start -> {
                    Player.FirstPlayer(userId)
                        .also { firstPlayerStartGameUseCase.execute(it) }
                }
            }
            emit(NewGame.GameReady(player))
        }.onStart { emit(NewGame.Loading) }
            .catchTyped(PlayerException::class) { emit(NewGame.Failure) }
            .flowOn(dispatcherProvider.io())

    private fun processRecoverGame(): Flow<RecoverGameAttempt> =
        flow {
            val sessionId = recoverGameUseCase.execute()
            sessionId?.let {
                emit(GameSessionFound(it))
            } ?: emit(NoGameAvailable)
        }.onStart { emit(Loading) }
            .catchTyped(PlayerException::class) { emit(NoGameAvailable) }
            .flowOn(dispatcherProvider.io())

    private fun processRouteToLeaderboard(): Flow<NavigateToLeaderboard> =
        flow {
            emit(NavigateToLeaderboard)
        }

    private sealed class NewGameOption {
        object Start : NewGameOption()
        object Join : NewGameOption()
    }

}
