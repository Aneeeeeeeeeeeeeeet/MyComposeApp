package com.example.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Message
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.User

@Composable
fun MessageItem(
    message: com.example.myapplication.model.Message,
    onLongClick: () -> Unit = {},
    onEdit: (String) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    var newText by remember { mutableStateOf(message.text) }

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
            Text(text = "Сообщение: ${message.text}")
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { showDialog = true }) {
                Text("Edit")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit Message") },
            text = {
                TextField(
                    value = newText,
                    onValueChange = { newText = it }
                )
            },
            confirmButton = {
                Button(onClick = {
                    onEdit(newText)
                    showDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }
}

