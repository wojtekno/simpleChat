package com.example.core.usecases

import com.example.core.domain.ChatMessage

interface SendMessageUseCase {
    suspend fun execute(message: ChatMessage)
}
