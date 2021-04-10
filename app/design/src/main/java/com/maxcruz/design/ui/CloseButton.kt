package com.maxcruz.design.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxcruz.design.R

/**
 * Corner close button, navigate up
 */
@Composable
fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.Close,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.padding(all = 0.dp),
    ) {
        Icon(imageVector, stringResource(R.string.close_button))
    }
}