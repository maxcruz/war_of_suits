package com.maxcruz.player.presentation.join.mvi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
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
import com.maxcruz.design.ui.InputField
import com.maxcruz.design.ui.ScaffoldPage
import com.maxcruz.design.ui.ShowAnimated
import com.maxcruz.player.R

data class JoinViewState(
    private val input: String = "",
    private val hasError: Boolean = false,
    private val isLoading: Boolean = false,
) : MVIViewState<JoinIntent> {

    @Composable
    override fun Render(action: (JoinIntent) -> Unit) {

        ScaffoldPage(
            isLoading = isLoading,
            onClose = {
                action(JoinIntent.CloseGame)
            }
        ) {
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
                        modifier = Modifier.padding(all = 24.dp),
                        textAlign = TextAlign.Center,
                    )

                    // Input
                    InputField(
                        placeholder = "#",
                        modifier = Modifier.padding(horizontal = 96.dp),
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

@Preview
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        JoinViewState().Render {}
    }
}