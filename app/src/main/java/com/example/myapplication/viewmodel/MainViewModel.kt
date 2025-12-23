package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.myapplication.model.FeedItem


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = AppRepository(
        database.userDao(),
        database.messageDao()
    )

    private val _feed = MutableStateFlow<List<FeedItem>>(emptyList())
    val feed: StateFlow<List<FeedItem>> = _feed

    private var userCounter = 1
    private var messageCounter = 1

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val users = repository.getUsers()
            val messages = repository.getMessages()

            val combined = mutableListOf<Pair<Long, FeedItem>>()

            users.forEach {
                combined.add(
                    it.createdAt to FeedItem.UserItem(it.id, it.name)
                )
            }

            messages.forEach {
                combined.add(
                    it.createdAt to FeedItem.MessageItem(it.id, it.text)
                )
            }

            _feed.value = combined
                .sortedBy { it.first }
                .map { it.second }
        }
    }


    fun addUser() {
        viewModelScope.launch {
            val name = "User $userCounter"
            userCounter++
            repository.addUser(name)
            loadData()
        }
    }

    fun addMessage(userId: Int) {
        viewModelScope.launch {
            val text = "Message $messageCounter"
            messageCounter++
            repository.addMessage(text, userId)
            loadData()
        }
    }
    fun deleteUserById(id: Int) {
        viewModelScope.launch {
            val user = repository.getUsers().firstOrNull { it.id == id }
            if (user != null) {
                repository.deleteUser(user)
                loadData()
            }
        }
    }

    fun deleteMessageById(id: Int) {
        viewModelScope.launch {
            val message = repository.getMessages().firstOrNull { it.id == id }
            if (message != null) {
                repository.deleteMessage(message)
                loadData()
            }
        }
    }
    fun updateUser(id: Int, newName: String) {
        viewModelScope.launch {
            val user = repository.getUsers().firstOrNull { it.id == id }
            if (user != null) {
                repository.updateUser(user.copy(name = newName))
                loadData()
            }
        }
    }

    fun updateMessage(id: Int, newText: String) {
        viewModelScope.launch {
            val message = repository.getMessages().firstOrNull { it.id == id }
            if (message != null) {
                repository.updateMessage(message.copy(text = newText))
                loadData()
            }
        }
    }

}
