package com.example.simplechat.framework.db

import com.example.core.data.MessageLocalDataSource
import com.example.core.domain.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomMessageDataSource @Inject constructor(private val messageDao: ChatMessageDao) : MessageLocalDataSource {
    override fun getMessages(chatId: Int): Flow<List<ChatMessage>> {
        return messageDao.getMessagesForChatId(chatId)
            .map { messages -> messages.map { it.toModel() } }
    }

    override fun getMessages(userId: Int, chatId: Int): Flow<List<ChatMessage>> {
        return messageDao.getMessagesForChatId(userId, chatId)
            .map { messages -> messages.map { it.toModel() } }
    }

    override suspend fun insertAll(messages: List<ChatMessage>) {
        messageDao.insertMessages(messages.map { it.toEntity() })
    }

    override suspend fun insert(message: ChatMessage) {
        messageDao.insert(message.toEntity())
    }
}
