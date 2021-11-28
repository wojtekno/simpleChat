package com.example.core.data

import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow

interface MessageLocalDataSource {
    fun getMessages(chatId: Int): Flow<List<ChatMessage>>

    suspend fun insertAll(messages: List<ChatMessage>)
}