package com.maxcruz.player.presentation.join.process

import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.model.Role
import com.maxcruz.player.domain.usecase.SecondPlayerJoinSessionUseCase
import com.maxcruz.player.presentation.join.mvi.JoinIntent
import com.maxcruz.player.presentation.join.mvi.JoinResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinProcessHolder @Inject constructor(
    private val secondPlayerJoinSessionUseCase: SecondPlayerJoinSessionUseCase,
) : MVIProcessHolder<JoinIntent, JoinResult> {

    override fun processIntent(intent: JoinIntent): Flow<JoinResult> =
        when (intent) {
            is JoinIntent.InputCode -> flow {
                val session = if (intent.code.isNotBlank()) {
                    secondPlayerJoinSessionUseCase.execute(intent.code)
                } else {
                    null
                }
                if (session != null) {
                    emit(
                        JoinResult.SearchGame.Found(
                            sessionId = session.sessionId,
                            player = Player(session.userId, Role.FIRST)
                        )
                    )
                } else {
                    emit(JoinResult.SearchGame.NotFound)
                }
            }
            else -> throw UnexpectedIntentException(intent)
        }
}