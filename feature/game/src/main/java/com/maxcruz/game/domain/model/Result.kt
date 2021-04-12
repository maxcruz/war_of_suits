package com.maxcruz.game.domain.model

sealed class Result {
    object Win: Result()
    object Lose: Result()
    object Draw: Result()
}
