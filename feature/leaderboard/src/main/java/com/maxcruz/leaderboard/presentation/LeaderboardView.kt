package com.maxcruz.leaderboard.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxcruz.leaderboard.R

@Composable
fun LeaderboardView(
    viewModel: LeaderboardViewModel,
    actionNavigateUp: () -> Unit,
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            IconButton(
                onClick = { actionNavigateUp() },
                modifier = Modifier.align(Alignment.TopStart),
            ) {
                Icon(Icons.Default.Close, stringResource(R.string.leaderboard_close_button))
            }
            Text(
                text = stringResource(R.string.leaderboard_title),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}