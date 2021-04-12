package com.maxcruz.player.presentation.waiting.mvi

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.ScaffoldPage
import com.maxcruz.design.ui.ShowAnimated
import com.maxcruz.player.R

data class WaitingViewState(
    private val code: String? = null,
) : MVIViewState<WaitingIntent> {

    @Composable
    override fun Render(action: (WaitingIntent) -> Unit) {
        ScaffoldPage(
            isLoading = (code == null),
            onClose = {
                action(WaitingIntent.CloseGame)
            }
        ) {

            // Message
            val text = with(AnnotatedString.Builder(stringResource(R.string.waiting_title))) {
                pushStyle(SpanStyle(color = MaterialTheme.colors.primary))
                append(code ?: "")
                pop()
                append(stringResource(R.string.waiting_title_complement))
                toAnnotatedString()
            }

            ShowAnimated(
                visible = (code != null),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        WaitingViewState(
            code = "44695",
        ).Render {}
    }
}