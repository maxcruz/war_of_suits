package com.maxcruz.warofsuits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.warofsuits.navigation.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarOfSuitsTheme {
                window.statusBarColor = MaterialTheme.colors.background.toArgb()
                window.navigationBarColor = MaterialTheme.colors.background.toArgb()
                RootNavigationGraph()
            }
        }
    }
}
