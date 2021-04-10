package com.maxcruz.leaderboard.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxcruz.design.ui.CloseButton
import com.maxcruz.leaderboard.R

@Composable
fun LeaderboardView(viewModel: LeaderboardViewModel) {
    Scaffold {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)) {
            CloseButton(
                onClick = { viewModel.navigator.actionNavigateUp() },
                modifier = Modifier.align(Alignment.TopStart),
            )
            Text(
                text = stringResource(R.string.leaderboard_title),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}