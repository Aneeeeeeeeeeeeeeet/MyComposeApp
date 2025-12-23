package com.example.myapplication.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.User


@Composable
fun UserItem(
    user: com.example.myapplication.model.User,
    onLongClick: () -> Unit = {},
    onEdit: (String) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(user.name) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Пользователь: ${user.name}")
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { showDialog = true }) {
                Text("Edit")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit User") },
            text = {
                TextField(
                    value = newName,
                    onValueChange = { newName = it }
                )
            },
            confirmButton = {
                Button(onClick = {
                    onEdit(newName)
                    showDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }
}

