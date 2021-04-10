package com.maxcruz.design.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Closable screen with a loading and error state
 */
@Composable
fun ScaffoldPage(
    isLoading: Boolean = false,
    hasError: Boolean = false,
    @StringRes errorMessage: Int? = null,
    closeAlignment: Alignment = Alignment.TopStart,
    onClose: (() -> Unit)? = null,
    closeImage: ImageVector = Icons.Default.Close,
    content: @Composable BoxScope.() -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (onClose != null) {
                CloseButton(
                    onClick = { onClose() },
                    modifier = Modifier.align(closeAlignment)
                        .padding(horizontal = 16.dp),
                    imageVector = closeImage,
                )
            }

            content()

            ShowAnimated(
                visible = isLoading,
                modifier = Modifier.align(Alignment.Center),
            ) {
                CircularProgressIndicator()
            }

            ErrorSnackbar(
                state = scaffoldState.snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            }
            if (hasError && errorMessage != null) {
                val message = stringResource(errorMessage)
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(message)
                }
            }
        }
    }
}