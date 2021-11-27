package com.example.core.usecases

import com.example.core.data.DummyData
import com.example.core.domain.ChatMessage
import javax.inject.Inject

class GetMessagesUseCaseImpl @Inject constructor() : GetMessagesUseCase {
    override suspend fun execute(chatId: Int): List<ChatMessage> {
        return DummyData().createChatMessagesDummy()
    }
}
