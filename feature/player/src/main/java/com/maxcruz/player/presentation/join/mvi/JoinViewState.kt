package com.maxcruz.player.presentation.join.mvi

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.CloseButton
import com.maxcruz.design.ui.InputField
import com.maxcruz.design.ui.ShowAnimated
import com.maxcruz.player.R

data class JoinViewState(
    private val input: String = "",
    private val hasError: Boolean = false,
    private val isLoading: Boolean = false,
) : MVIViewState<JoinIntent> {

    @Composable
    override fun Render(action: (JoinIntent) -> Unit) {
        Scaffold {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                // Close
                CloseButton(
                    onClick = { action(JoinIntent.CloseGame) },
                    modifier = Modifier.align(Alignment.TopStart),
                )

                // Progress indicator
                ShowAnimated(
                    visible = isLoading,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }

                ShowAnimated(
                    visible = !isLoading,
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // Message
                        Text(
                            text = stringResource(R.string.join_title),
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.padding(all = 16.dp),
                            textAlign = TextAlign.Center,
                        )

                        // Input
                        InputField(
                            placeholder = "#",
                            modifier = Modifier.padding(horizontal = 72.dp),
                            isError = !hasError,
                            onDone = {
                                action(JoinIntent.InputCode(code = it))
                            }
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
        JoinViewState().Render {}
    }
}