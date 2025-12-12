package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyApplicationApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun MyApplicationApp() {


    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.SCREEN_A) }

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

enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    SCREEN_A("Screen A", Icons.Default.Home),
    SCREEN_B("Screen B", Icons.Default.Favorite)
}

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

        Divider()

        Box(modifier = Modifier
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

@Composable
fun SubScreen1(modifier: Modifier = Modifier, onNavigateToSecond: () -> Unit) {

    var labelText by rememberSaveable { mutableStateOf("Начальный текст (Подэкран1)") }

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
fun SubScreen2(modifier: Modifier = Modifier, onNavigateBack: () -> Unit) {

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
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))


        Button(onClick = onNavigateBack) {
            Text("Назад к Подэкран1")
        }
    }
}

@Composable
fun ScreenB(modifier: Modifier = Modifier) {

    var labelText by rememberSaveable { mutableStateOf("Текст на экране B") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = labelText, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(30.dp))
        Button(onClick = { labelText = "Изменено на экране B!" }) {
            Text("Обновить метку")
        }
    }
}

