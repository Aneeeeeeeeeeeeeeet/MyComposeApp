package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.myapplication.navigation.AppDestinations
import com.example.myapplication.screens.ScreenA
import com.example.myapplication.screens.ScreenB

@Composable
fun MyApplicationApp() {

    var currentDestination by rememberSaveable {
        mutableStateOf(AppDestinations.SCREEN_A)
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(it.icon, contentDescription = it.label) },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.SCREEN_A -> ScreenA(Modifier.padding(innerPadding))
                AppDestinations.SCREEN_B -> ScreenB(Modifier.padding(innerPadding))
            }
        }
    }
}
