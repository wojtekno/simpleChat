package com.example.core.usecases

import com.example.core.data.MessageRepository
import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesImpl @Inject constructor(private val messageRepository: MessageRepository) : GetMessagesUseCase {
    override fun execute(userId: Int, chatId: Int): Flow<List<ChatMessage>> {
        return messageRepository.getMessages(userId, chatId)
    }
}
