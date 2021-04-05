package com.maxcruz.player.presentation.join

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
fun JoinView(
    viewModel: JoinViewModel,
    actionNavigateToGame: (String) -> Unit,
    actionNavigateUp: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Player: XXX",
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrincipalButton(
                onClick = { actionNavigateToGame("GAME2") },
                text = "Game"
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrincipalButton(
                onClick = { actionNavigateUp() },
                text = "Close"
            )

        }
    }
}
