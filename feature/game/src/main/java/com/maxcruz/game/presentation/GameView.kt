package com.maxcruz.game.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxcruz.design.ui.PrincipalButton
import com.maxcruz.core.domain.model.Player

@Composable
fun GameView(
    viewModel: GameViewModel,
    sessionId: String,
    player: Player,
) {



    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "COUNTER: 0",
            )
            Spacer(modifier = Modifier.size(16.dp))
            PrincipalButton(
                onClick = { viewModel.navigator.actionNavigateUp()  },
                text = "Close"
            )

        }
    }
}
