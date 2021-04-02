package com.maxcruz.player.presentation.start

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxcruz.design.ui.PrincipalButton
import com.maxcruz.player.R

@Composable
fun StartView(
    viewModel: StartViewModel,
    actionNavigateToWaiting: (String) -> Unit,
    actionNavigateToJoin: (String) -> Unit,
    actionNavigateToLeaderboard: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.start_title),
                style = MaterialTheme.typography.h4
            )

            /*if (isLoading) {
                CircularProgressIndicator()
            }*/

            Column {
                PrincipalButton(
                    onClick = { actionNavigateToWaiting("RIU") },
                    text = stringResource(R.string.start_button_new),
                    //enabled = !isLoading,
                )
                Spacer(modifier = Modifier.size(16.dp))
                PrincipalButton(
                    onClick = { actionNavigateToJoin("KEN") },
                    text = stringResource(R.string.start_button_join),
                    //enabled = !isLoading,
                )
                Spacer(modifier = Modifier.size(16.dp))
                PrincipalButton(
                    onClick = { actionNavigateToLeaderboard() },
                    text = stringResource(R.string.start_button_leaderboard),
                    //enabled = !isLoading,
                )
            }
        }
    }
}
