package com.maxcruz.player.presentation.waiting.process

import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WaitingProcessHolder @Inject constructor() : MVIProcessHolder<WaitingIntent, WaitingResult> {

    override fun processIntent(intent: WaitingIntent): Flow<WaitingResult> {
        return when (intent) {
            is WaitingIntent.CloseGame -> flow { emit(GameClosed) }
            is WaitingIntent.Load -> flow { emit(ShowCode(intent.code)) }
            is WaitingIntent.SecondPlayerJoined -> flow { emit(GameSession(intent.sessionId)) }
        }
    }
}