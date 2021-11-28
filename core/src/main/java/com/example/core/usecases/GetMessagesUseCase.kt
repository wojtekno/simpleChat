package com.example.core.usecases

import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow

interface GetMessagesUseCase {
    fun execute(chatId: Int): Flow<List<ChatMessage>>
    fun execute2(userId: Int, chatId: Int): Flow<List<ChatMessage>>
}
