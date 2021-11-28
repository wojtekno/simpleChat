package com.example.simplechat.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {

    @Query("SELECT * FROM chat_message WHERE (receiverId = :chatId OR senderId = :chatId)")
    fun getMessagesForChatId(chatId: Int): Flow<List<ChatMessageEntity>>

    @Insert
    fun insertMessages(messages: List<ChatMessageEntity>)

    @Insert
    fun insert(message: ChatMessageEntity)
}
