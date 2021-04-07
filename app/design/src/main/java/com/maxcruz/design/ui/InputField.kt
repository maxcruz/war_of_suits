package com.maxcruz.design.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    onDone: (String) -> Unit = {},
) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        isError = isError,
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,
            )
        },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
        ),
        textStyle = MaterialTheme.typography.h4,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Characters,
            imeAction = ImeAction.Done,
        ),
        onValueChange = { text = it },
        keyboardActions = KeyboardActions(
            onDone = { onDone(text) }
        ),
    )
}