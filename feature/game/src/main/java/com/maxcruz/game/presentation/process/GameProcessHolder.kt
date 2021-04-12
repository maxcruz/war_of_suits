package com.maxcruz.game.presentation.process

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.extensions.catchTyped
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.game.domain.error.GameException
import com.maxcruz.game.domain.usecase.AskOpponentCardUseCase
import com.maxcruz.game.domain.usecase.DealGameUseCase
import com.maxcruz.game.domain.usecase.ResolveRoundUseCase
import com.maxcruz.game.domain.usecase.WaitCardsUseCase
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameIntent.*
import com.maxcruz.game.presentation.mvi.GameResult
import com.maxcruz.game.presentation.mvi.GameResult.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GameProcessHolder @Inject constructor(
    private val dealGameUseCase: DealGameUseCase,
    private val waitCardsUseCase: WaitCardsUseCase,
    private val askOpponentCardUseCase: AskOpponentCardUseCase,
    private val resolveRoundUseCase: ResolveRoundUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : MVIProcessHolder<GameIntent, GameResult> {

    override fun processIntent(intent: GameIntent): Flow<GameResult> =
        when (intent) {
            is Load -> processGameLoad(intent)
            is PlayCard -> processPlayCard(intent)
            is Close -> processClose()
            is GameIntent.FinishGame -> TODO()
        }

    private fun processPlayCard(intent: PlayCard): Flow<Round> =
        flow {
            val sessionId = intent.sessionId
            val playingCard = intent.card
            val opponentCard = askOpponentCardUseCase.execute(sessionId)
            emit(Round.OpponentCard(opponentCard))
            delay(3000)
            val winner = resolveRoundUseCase.execute(sessionId, playingCard, opponentCard)
            val won = (winner is ResolveRoundUseCase.Winner.First)
            emit(Round.RoundResult(won))
        }.catchTyped(GameException::class) { emit(Round.Failure) }
            .flowOn(dispatcherProvider.io())

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun processGameLoad(intent: Load): Flow<GameLoad> =
        flow {
            emit(GameLoad.Loading)
            val (sessionId, dealer) = intent
            if (dealer) {
                val hand = dealGameUseCase.execute(sessionId)
                emit(GameLoad.GameReady(sessionId, hand))
            } else {
                val hand = waitCardsUseCase.execute(sessionId)
                merge(hand)
                    .map { GameLoad.GameReady(sessionId, it) }
            }
        }.onStart { emit(GameLoad.Loading) }
            .catchTyped(GameException::class) { emit(GameLoad.Failure) }
            .flowOn(dispatcherProvider.default())

    private fun processClose(): Flow<GameResult> = flow {
        // Call use-case to inform the end of the game
        emit(Closed)
    }

    private fun processGameFinish(points: Int?): Flow<GameResult> = flow {
        // Call use-case to inform the points and restart
        emit(Closed)
    }
}