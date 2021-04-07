package com.maxcruz.design.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Informative error message when an action is not required
 */
@Composable
fun ErrorSnackbar(
    state: SnackbarHostState,
    modifier: Modifier = Modifier,
    onAction: () -> Unit = {},
) {
    SnackbarHost(
        hostState = state,
        modifier = modifier,
    ) { data ->
        Snackbar(
            backgroundColor = MaterialTheme.colors.error,
            contentColor = MaterialTheme.colors.onError,
            modifier = Modifier.padding(16.dp),
            action = {
                data.actionLabel?.let { actionLabel ->
                    TextButton(onClick = { onAction() }) {
                        Text(
                            text = actionLabel,
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
            }
        ) {
            Text(
                text = data.message,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
