package com.maxcruz.player.presentation.start.mvi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.ErrorSnackbar
import com.maxcruz.design.ui.PrincipalButton
import com.maxcruz.player.R
import kotlinx.coroutines.launch

data class StartViewState(
    private val isLoading: Boolean = false,
    private val hasError: Boolean = false,
) : MVIViewState<StartIntent> {

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun Render(action: (StartIntent) -> Unit) {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
        ) {
            Box(modifier = Modifier) {

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

                // Progress indicator
                AnimatedVisibility(
                    visible = isLoading,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 150.dp),
                ) {
                    CircularProgressIndicator()
                }

                // Show error status
                ErrorSnackbar(
                    state = scaffoldState.snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter),
                ) {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
                if (hasError) {
                    val message = stringResource(R.string.start_error_message)
                    val button = stringResource(R.string.start_button_hide)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = button,
                        )
                    }
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
