package com.maxcruz.warofsuits.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Gray100 = Color(0xFFF5F5F5)
val Gray900 = Color(0xFF212121)
val Red300 = Color(0xFFE57373)
val PinkDark = Color(0xFFFF375F)

val ColorPalette = darkColors(
    primary = PinkDark,
    background = Gray900,
    surface = Gray100,
    onError = Red300,
    onBackground = Gray100,
    onPrimary = White,
    onSurface = Black,
)
