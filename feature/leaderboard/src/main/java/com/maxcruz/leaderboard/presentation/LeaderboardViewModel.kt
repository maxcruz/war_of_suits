package com.maxcruz.leaderboard.presentation

import androidx.lifecycle.ViewModel
import com.maxcruz.leaderboard.navigation.LeaderboardNavigator
import javax.inject.Inject

class LeaderboardViewModel @Inject constructor() : ViewModel() {

    lateinit var navigator: LeaderboardNavigator
}