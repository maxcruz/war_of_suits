package com.maxcruz.design.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun SuitIcon(suit: String) {
    Card(
        modifier = Modifier.size(36.dp),
        shape = CircleShape,
        elevation = 2.dp
    ) {
        SuitImage(
            suit = suit,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        )
    }
}