package com.maxcruz.player.presentation.waiting.process

import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.model.Role
import com.maxcruz.player.domain.usecase.RetrieveSessionByCodeUseCase
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.GameSession
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.ShowCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class WaitingProcessHolder @Inject constructor(
    private val retrieveSessionByCodeUseCase: RetrieveSessionByCodeUseCase
) : MVIProcessHolder<WaitingIntent, WaitingResult> {

    override fun processIntent(intent: WaitingIntent): Flow<WaitingResult> =
        when (intent) {
            is WaitingIntent.Load -> flow {
                emit(ShowCode(intent.code))

                // This event is going to be received when the player 2 enter
                delay(5000)
                with(retrieveSessionByCodeUseCase.execute(intent.code)) {
                    if (this?.firstPlayer != null) {
                        val playerId = firstPlayer
                            .takeLast(5)
                            .toUpperCase(Locale.getDefault())
                        emit(GameSession(sessionId, Player(playerId, Role.FIRST)))
                    } else {
                        throw UnexpectedIntentException(intent)
                    }
                }
            }
            else -> throw UnexpectedIntentException(intent)
        }
}