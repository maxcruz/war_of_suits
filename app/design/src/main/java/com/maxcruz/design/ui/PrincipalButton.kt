package com.maxcruz.design.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Button to show the call to action
 */
@Composable
fun PrincipalButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.width(257.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
        )
    }
}
