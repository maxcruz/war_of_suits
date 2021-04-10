package com.maxcruz.game.presentation

import com.maxcruz.core.presentation.MVIViewModel
import com.maxcruz.game.navigation.GameNavigator
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameResult
import com.maxcruz.game.presentation.mvi.GameViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(

) : MVIViewModel<GameIntent, GameViewState, GameResult, GameNavigator>(
    initialState = GameViewState(isLoading = true),
) {

    override lateinit var navigator: GameNavigator

    override suspend fun transformer(intent: GameIntent): Flow<GameResult> {
        TODO("Not yet implemented")
    }

    override suspend fun reducer(previous: GameViewState, result: GameResult): GameViewState {
        TODO("Not yet implemented")
    }

}