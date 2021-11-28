package com.example.core.usecases

import com.example.core.data.MessageRepository
import com.example.core.domain.ChatMessage
import javax.inject.Inject

class SendMessageImpl @Inject constructor(private val messageRepository: MessageRepository) : SendMessageUseCase {
    override suspend fun execute(message: ChatMessage) {
        messageRepository.insert(message)
    }
}
