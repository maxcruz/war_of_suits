package com.maxcruz.game.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxcruz.design.ui.PrincipalButton

@Composable
fun GameView(
    viewModel: GameViewModel,
    sessionId: String,
    actionNavigateUp: () -> Unit,
) {
    val viewState = remember(viewModel) { viewModel.counter }.collectAsState(initial = 0)

    // TODO: Idea apply state hoisting moving the render state to the view function in GameViewState
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "COUNTER: ${viewState.value}",
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrincipalButton(
                onClick = { actionNavigateUp() },
                text = "Close"
            )

        }
    }
}
