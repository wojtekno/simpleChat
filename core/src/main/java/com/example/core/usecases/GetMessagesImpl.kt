package com.example.core.usecases

import com.example.core.data.MessageRepository
import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow
import java.util.logging.Logger
import javax.inject.Inject

class GetMessagesImpl @Inject constructor(private val messageRepository: MessageRepository) : GetMessagesUseCase {
    override fun execute(chatId: Int): Flow<List<ChatMessage>> {
        Logger.getLogger(this.javaClass.name).config("execute")
        return messageRepository.getMessages(chatId)
    }

    override fun execute2(userId: Int, chatId: Int): Flow<List<ChatMessage>> {
        return messageRepository.getMessages(userId, chatId)
    }
}
