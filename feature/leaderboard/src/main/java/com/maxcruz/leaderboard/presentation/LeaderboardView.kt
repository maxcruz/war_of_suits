package com.maxcruz.leaderboard.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maxcruz.design.ui.ScaffoldPage
import com.maxcruz.leaderboard.R

@Composable
fun LeaderboardView(viewModel: LeaderboardViewModel) {
    ScaffoldPage(
        onClose = {
            viewModel.navigator.actionNavigateUp()
        }
    ) {
        Text(
            text = stringResource(R.string.leaderboard_title),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}