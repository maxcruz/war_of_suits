package com.maxcruz.player.presentation.join.mvi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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
import com.maxcruz.design.ui.CloseButton
import com.maxcruz.player.R

data class JoinViewState(
    private val input: String = "",
    private val hasError: Boolean = false,
    private val isLoading: Boolean = false,
) : MVIViewState<JoinIntent> {

    @OptIn(ExperimentalAnimationApi::class)
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



                // Message
                val text = with(AnnotatedString.Builder(stringResource(R.string.waiting_title))) {
                    pushStyle(SpanStyle(color = MaterialTheme.colors.primary))
                    append("")
                    pop()
                    append(stringResource(R.string.waiting_title_complement))
                    toAnnotatedString()
                }
                AnimatedVisibility(
                    enter = fadeIn(animationSpec = tween(durationMillis = 200)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 200)),
                    visible = true,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
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

}

@Preview
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        JoinViewState().Render {}
    }
}