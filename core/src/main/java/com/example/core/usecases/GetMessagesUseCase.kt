package com.example.core.usecases

import com.example.core.domain.ChatMessage

interface GetMessagesUseCase {
    suspend fun execute(chatId: Int): List<ChatMessage>
}
