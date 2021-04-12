package com.maxcruz.design.ui

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.SwipeAnimation(
    swipeState: SwipeableState<Int>,
    animation: @Composable (Map<Float, Int>, Float) -> Unit
) {
    val halfPx = with(LocalDensity.current) { ((maxWidth / 2) - 110.dp).toPx() }
    val startAnchor = with(LocalDensity.current) { -(180.dp).toPx() }
    val anchors = mapOf(startAnchor to 0, halfPx to 1)
    val angle: Float = try {
        val fraction = swipeState.progress.fraction
        if (swipeState.progress.to == 1) {
            (1 - fraction) * 5f
        } else {
            (fraction) * 5f
        }
    } catch (e: NoSuchElementException) {
        // Report non-fatal to crashlytics and fallback angle animation
        e.printStackTrace()
        0f
    }
    animation(anchors, angle)
}