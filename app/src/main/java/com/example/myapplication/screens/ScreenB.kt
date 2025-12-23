package com.example.myapplication.screens

import android.app.Application
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.components.MessageItem
import com.example.myapplication.components.UserItem
import com.example.myapplication.model.FeedItem
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun ScreenB(modifier: Modifier = Modifier) {

    val viewModel: MainViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            LocalContext.current.applicationContext as Application
        )
    )

    val feed by viewModel.feed.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {

        Text(
            text = "Экран B — пользователи и сообщения (SQLite)",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        Row(modifier = Modifier.padding(8.dp)) {

            Button(onClick = { viewModel.addUser() }) {
                Text("Add User")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                enabled = feed.any { it is FeedItem.UserItem },
                onClick = {
                    val user = feed.filterIsInstance<FeedItem.UserItem>().first()
                    viewModel.addMessage(user.id.toInt())
                }
            ) {
                Text("Add Message")
            }
        }

        LazyColumn {
            items(feed) { item ->
                when (item) {
                    is FeedItem.UserItem -> {
                        UserItem(
                            user = com.example.myapplication.model.User(item.id, item.name),
                            onLongClick = { viewModel.deleteUserById(item.id) },
                            onEdit = { newName -> viewModel.updateUser(item.id, newName) }
                        )
                    }

                    is FeedItem.MessageItem -> {
                        MessageItem(
                            message = com.example.myapplication.model.Message(item.id, item.text),
                            onLongClick = { viewModel.deleteMessageById(item.id) },
                            onEdit = { newText -> viewModel.updateMessage(item.id, newText) }
                        )
                    }
                }
            }
        }
    }
}
