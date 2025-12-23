package com.example.myapplication.model

sealed class FeedItem {
    data class UserItem(val id: Int, val name: String) : FeedItem()
    data class MessageItem(val id: Int, val text: String) : FeedItem()
}
