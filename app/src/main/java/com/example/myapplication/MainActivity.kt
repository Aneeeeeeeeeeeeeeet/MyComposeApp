package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun SubScreen1(modifier: Modifier = Modifier, onNavigateToSecond: () -> Unit) {

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
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateBack) {
            Text("Назад к Подэкран1")
        }
    }
}



data class User(
    val id: Int,
    val name: String
)

data class Message(
    val id: Int,
    val text: String
)



@Composable
fun UserWithMessageItem(
    user: User,
    message: Message?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Пользователь: ${user.name}",
                style = MaterialTheme.typography.bodyLarge
            )

            if (message != null) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "СМС: ${message.text}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}



@Composable
fun ScreenB(modifier: Modifier = Modifier) {

    val users = listOf(
        User(1, "User1"),
        User(2, "User2"),
        User(2, "User2"),
        User(2, "User2"),
        User(2, "User2"),
        User(2, "User2"),
        User(2, "User2"),
        User(2, "User2"),
        User(3, "User3")
    )

    val messages = listOf(
        Message(1, "Сообщение 1"),
        Message(2, "Сообщение 2"),
        Message(3, "Сообщение 3")
    )

    Column(modifier = modifier.fillMaxSize()) {

        Text(
            text = "Экран B — пользователи и сообщения",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(users) { user ->
                val matchedMessage = messages.find { it.id == user.id }

                UserWithMessageItem(
                    user = user,
                    message = matchedMessage
                )
            }
        }
    }
}
