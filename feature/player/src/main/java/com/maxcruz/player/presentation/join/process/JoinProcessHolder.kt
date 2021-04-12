package com.maxcruz.player.presentation.join.process

import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.domain.usecase.PlayerJoinSessionUseCase
import com.maxcruz.player.presentation.join.mvi.JoinIntent
import com.maxcruz.player.presentation.join.mvi.JoinResult
import com.maxcruz.player.presentation.join.mvi.JoinResult.SearchGame.Found
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinProcessHolder @Inject constructor(
    private val playerJoinSessionUseCase: PlayerJoinSessionUseCase,
) : MVIProcessHolder<JoinIntent, JoinResult> {

    override fun processIntent(intent: JoinIntent): Flow<JoinResult> =
        when (intent) {
            is JoinIntent.InputCode -> flow {
                val session = if (intent.code.isNotBlank()) {
                    playerJoinSessionUseCase.execute(intent.code)
                } else {
                    null
                }
                if (session != null) {
                    emit(Found(sessionId = session.sessionId))
                } else {
                    emit(JoinResult.SearchGame.NotFound)
                }
            }
            else -> throw UnexpectedIntentException(intent)
        }
}