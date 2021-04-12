package com.maxcruz.player.presentation.waiting.process

import com.maxcruz.core.coroutines.DispatcherProvider
import com.maxcruz.core.error.UnexpectedIntentException
import com.maxcruz.core.presentation.process.MVIProcessHolder
import com.maxcruz.player.domain.usecase.WaitSecondPlayerUseCase
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.GameSession
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult.ShowCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WaitingProcessHolder @Inject constructor(
    private val waitSecondPlayerUseCase: WaitSecondPlayerUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : MVIProcessHolder<WaitingIntent, WaitingResult> {

    override fun processIntent(intent: WaitingIntent): Flow<WaitingResult> =
        when (intent) {
            is WaitingIntent.Load -> processLoadIntent(intent)
            else -> throw UnexpectedIntentException(intent)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun processLoadIntent(intent: WaitingIntent.Load): Flow<WaitingResult> =
        waitSecondPlayerUseCase.execute(intent.code)
            .map { GameSession(it) }
            .onStart<WaitingResult> { emit(ShowCode(intent.code)) }
            .flowOn(dispatcherProvider.default())
}