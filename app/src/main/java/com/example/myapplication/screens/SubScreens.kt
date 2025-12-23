package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubScreen1(
    modifier: Modifier = Modifier,
    onNavigateToSecond: () -> Unit
) {

    var labelText by rememberSaveable {
        mutableStateOf("Начальный текст (Подэкран1)")
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = labelText, style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { labelText = "Текст изменён на Подэкран1" }) {
            Text("Изменить текст")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateToSecond) {
            Text("Перейти на Подэкран2")
        }
    }
}

@Composable
fun SubScreen2(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Подэкран2 — произвольная разметка",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Какая-то информация",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateBack) {
            Text("Назад к Подэкран1")
        }
    }
}
