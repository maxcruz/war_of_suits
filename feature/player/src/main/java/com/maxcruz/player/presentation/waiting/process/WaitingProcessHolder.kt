package com.maxcruz.player.presentation.waiting.process

import com.maxcruz.core.domain.model.Player
import com.maxcruz.core.domain.model.Role
import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.GameSession
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.ShowCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WaitingProcessHolder @Inject constructor() : MVIProcessHolder<WaitingIntent, WaitingResult> {

    override fun processIntent(intent: WaitingIntent): Flow<WaitingResult> =
        when (intent) {
            is WaitingIntent.Load -> flow {
                emit(ShowCode(intent.code))

                // This event is going to be received when the player 2 enter
                println("Waiting")
                delay(5000)
                println("Joined")
                val result = GameSession("xyz123456", Player("1234", Role.FIRST))
                emit(result)
            }
            else -> throw UnexpectedIntentException(intent)
        }
}