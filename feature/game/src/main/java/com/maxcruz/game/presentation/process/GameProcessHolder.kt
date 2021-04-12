package com.maxcruz.game.presentation.process

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.extensions.catchTyped
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.game.domain.error.GameException
import com.maxcruz.game.domain.usecase.AskOpponentCardUseCase
import com.maxcruz.game.domain.usecase.DealGameUseCase
import com.maxcruz.game.domain.usecase.EndGameUseCase
import com.maxcruz.game.domain.usecase.ResolveRoundUseCase
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameIntent.*
import com.maxcruz.game.presentation.mvi.GameResult
import com.maxcruz.game.presentation.mvi.GameResult.*
import com.maxcruz.game.presentation.mvi.GameResult.Round.RoundRestarted
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GameProcessHolder @Inject constructor(
    private val dealGameUseCase: DealGameUseCase,
    private val askOpponentCardUseCase: AskOpponentCardUseCase,
    private val resolveRoundUseCase: ResolveRoundUseCase,
    private val endGameUseCase: EndGameUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : MVIProcessHolder<GameIntent, GameResult> {

    override fun processIntent(intent: GameIntent): Flow<GameResult> =
        when (intent) {
            is Load -> processGameLoad(intent)
            is PlayCard -> processPlayCard(intent)
            is RoundEnd -> processRoundEnd()
            is EndGame -> processGameEnd(intent)
            else -> throw UnexpectedIntentException(intent)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun processGameLoad(intent: Load): Flow<GameLoad> =
        flow {
            emit(GameLoad.Loading)
            val sessionId = intent.sessionId
            val hand = dealGameUseCase.execute(sessionId)
            emit(GameLoad.GameReady(sessionId, hand))
        }.onStart { emit(GameLoad.Loading) }
            .catchTyped(GameException::class) { emit(GameLoad.Failure) }
            .flowOn(dispatcherProvider.default())

    private fun processPlayCard(intent: PlayCard): Flow<Round> =
        flow {
            emit(Round.PlayingCard)
            val sessionId = intent.sessionId
            val playingCard = intent.card
            val opponentCard = askOpponentCardUseCase.execute(sessionId)
            // Give some time to the player to read the opponent's card before finish
            delay(500)
            emit(Round.OpponentCard(opponentCard))
            delay(2000)
            val pair = resolveRoundUseCase.execute(sessionId, playingCard, opponentCard)
            emit(Round.RoundResult(pair, playingCard))
        }.catchTyped(GameException::class) { emit(Round.Failure) }
            .flowOn(dispatcherProvider.io())

    private fun processRoundEnd(): Flow<Round> = flow { emit(RoundRestarted) }

    private fun processGameEnd(intent: EndGame): Flow<GameEnded> = flow {
        val result = endGameUseCase.execute(intent.sessionId)
        emit(GameEnded(result))
    }
}