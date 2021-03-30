package com.maxcruz.player.start

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.PrincipalButton
import com.maxcruz.player.R

@Composable
fun StartContent() {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.start_title),
                style = MaterialTheme.typography.h4
            )

            Column {
                PrincipalButton(text = stringResource(R.string.start_button_new))
                Spacer(modifier = Modifier.size(16.dp))
                PrincipalButton(text = stringResource(R.string.start_button_join))
                Spacer(modifier = Modifier.size(16.dp))
                PrincipalButton(text = stringResource(R.string.start_button_leaderboard))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        StartContent()
    }
}