package com.example.core.data

import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow
import java.util.logging.Logger
import javax.inject.Inject

class MessageRepository @Inject constructor(private val localDataSource: MessageLocalDataSource) {
    fun getMessages(chatId: Int): Flow<List<ChatMessage>> {
        return localDataSource.getMessages(chatId)
    }

    fun getMessages(userId: Int, chatId: Int): Flow<List<ChatMessage>> {
        return localDataSource.getMessages(userId, chatId)
    }

    suspend fun insert(message: ChatMessage) {
        localDataSource.insert(message)
    }
}
