package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    SCREEN_A("Screen A", Icons.Default.Home),
    SCREEN_B("Screen B", Icons.Default.Favorite)
}
