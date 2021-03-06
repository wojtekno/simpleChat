package com.example.core.usecases

import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow

interface GetMessagesUseCase {
    fun execute(userId: Int, chatId: Int): Flow<List<ChatMessage>>
}
