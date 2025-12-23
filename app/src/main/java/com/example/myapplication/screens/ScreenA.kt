package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenA(modifier: Modifier = Modifier) {

    var currentSubScreen by rememberSaveable { mutableStateOf(SubScreen.FIRST) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Экран A — внутренние подэкраны",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            when (currentSubScreen) {
                SubScreen.FIRST -> SubScreen1(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToSecond = { currentSubScreen = SubScreen.SECOND }
                )
                SubScreen.SECOND -> SubScreen2(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateBack = { currentSubScreen = SubScreen.FIRST }
                )
            }
        }
    }
}

enum class SubScreen { FIRST, SECOND }
