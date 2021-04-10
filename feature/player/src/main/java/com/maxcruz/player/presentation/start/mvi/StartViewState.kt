package com.maxcruz.player.presentation.start.mvi

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.PrincipalButton
import com.maxcruz.design.ui.ScaffoldPage
import com.maxcruz.player.R

data class StartViewState(
    private val isLoading: Boolean = false,
    private val hasError: Boolean = false,
) : MVIViewState<StartIntent> {

    @Composable
    override fun Render(action: (StartIntent) -> Unit) {
        ScaffoldPage(
            isLoading = isLoading,
            hasError = hasError,
            errorMessage = R.string.start_error_message,
        ) {

            // Main UI: Screen title and buttons
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.start_title),
                    style = MaterialTheme.typography.h4
                )
                Column {
                    PrincipalButton(
                        onClick = { action(StartIntent.CreateGame) },
                        text = stringResource(R.string.start_button_new),
                        enabled = !isLoading,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    PrincipalButton(
                        onClick = { action(StartIntent.JoinGame) },
                        text = stringResource(R.string.start_button_join),
                        enabled = !isLoading,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    PrincipalButton(
                        onClick = { action(StartIntent.RouteToLeaderboard) },
                        text = stringResource(R.string.start_button_leaderboard),
                        enabled = !isLoading,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        StartViewState(
            isLoading = false,
            hasError = false
        ).Render {}
    }
}
