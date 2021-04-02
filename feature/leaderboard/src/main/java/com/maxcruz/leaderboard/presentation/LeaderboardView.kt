package com.maxcruz.leaderboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxcruz.design.ui.PrincipalButton

@Composable
fun LeaderboardView(
    viewModel: LeaderboardViewModel,
    actionNavigateUp: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "TOP 10",
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrincipalButton(
                onClick = { actionNavigateUp() },
                text = "Close"
            )

        }
    }
}