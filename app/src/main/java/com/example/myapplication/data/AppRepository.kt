package com.example.myapplication.data

class AppRepository(
    private val userDao: UserDao,
    private val messageDao: MessageDao
) {

    suspend fun getUsers(): List<UserEntity> =
        userDao.getAllUsers()

    suspend fun getMessages(): List<MessageEntity> =
        messageDao.getAllMessages()

    suspend fun addUser(name: String) {
        userDao.insertUser(UserEntity(name = name))
    }

    suspend fun addMessage(text: String, userId: Int) {
        messageDao.insertMessage(
            MessageEntity(text = text, userId = userId)
        )
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    suspend fun deleteMessage(message: MessageEntity) {
        messageDao.deleteMessage(message)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    suspend fun updateMessage(message: MessageEntity) {
        messageDao.updateMessage(message)
    }
}
