package com.example.simplechat.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.ChatMessage

@Entity(tableName = "chat_message")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val senderId: Int,
    val receiverId: Int,
    val message: String,
    val isSeen: Boolean,
    val creationDate: Long
)

fun ChatMessageEntity.toModel(): ChatMessage {
    return ChatMessage(this.id!!, this.senderId, this.receiverId, this.message, this.isSeen, this.creationDate)
}

fun ChatMessage.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(null, this.senderId, this.receiverId, this.message, this.isSeen, this.creationDate)
}
