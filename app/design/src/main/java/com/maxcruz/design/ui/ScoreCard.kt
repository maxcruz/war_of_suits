package com.maxcruz.design.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoreCard(
    pointsPlayer: Int,
    pointsOpponent: Int,
    playerLabel: String,
    opponentLabel: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {

        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = pointsPlayer.toString(),
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = playerLabel,
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = MaterialTheme.typography.body2,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = pointsOpponent.toString(),
                    style = MaterialTheme.typography.h2,
                )
                Text(
                    text = opponentLabel,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        }
    }
}