package com.maxcruz.game.presentation

import androidx.lifecycle.ViewModel
import com.maxcruz.game.domain.Counter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(counter: Counter) : ViewModel() {

    val counter: Flow<Int> = counter.counterStream
}