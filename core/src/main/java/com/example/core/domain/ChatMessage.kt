package com.example.core.domain

data class ChatMessage(val id: Int, val senderId: Int, val receiverId: Int, val message: String, val isSeen: Boolean, val creationDate: Long)
