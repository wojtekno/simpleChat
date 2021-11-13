package com.example.simplechat.ui.chat.recycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.simplechat.ui.chat.model.ChatItem

class ChatItemDiffCallback : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
       return oldItem::class == newItem::class && oldItem.id() == newItem.id()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.content() == newItem.content()
    }

    override fun getChangePayload(oldItem: ChatItem, newItem: ChatItem): Any? {
        return oldItem.payload(newItem)
    }
}