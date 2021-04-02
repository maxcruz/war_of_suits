package com.maxcruz.game.domain

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Counter @Inject constructor() {

    val counterStream: Flow<Int> = flow {
        var counter = 1
        while(true) {
            Log.d("GameViewModel", counter.toString())
            emit(counter)
            counter++
            delay(3000)
        }
    }
}