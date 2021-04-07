package com.maxcruz.design.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
@OptIn(ExperimentalAnimationApi::class)
fun ShowAnimated(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)),
        visible = visible,
        modifier = modifier,
    ) {
        content()
    }
}