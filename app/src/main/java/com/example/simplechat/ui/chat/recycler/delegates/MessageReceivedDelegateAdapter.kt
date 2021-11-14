package com.example.simplechat.ui.chat.recycler.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.R
import com.example.simplechat.databinding.ItemChatMessageReceivedBinding
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.MessageReceived

class MessageReceivedDelegateAdapter :
    DelegateAdapter<MessageReceived, MessageReceivedDelegateAdapter.MessageReceivedViewHolder>(MessageReceived::class.java) {


    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val viewBinding = ItemChatMessageReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageReceivedViewHolder(viewBinding)
    }

    override fun bindViewHolder(model: MessageReceived, viewHolder: MessageReceivedViewHolder, payloads: List<ChatItem.Payloadable>) {
        when (val payload = payloads.firstOrNull() as? MessageReceived.ChangePayload) {

            is MessageReceived.ChangePayload.HasTailChanged ->
                viewHolder.bindHasTailChanged(payload.hasTail)

            else -> viewHolder.bind(model)
        }
    }

    inner class MessageReceivedViewHolder(private val messageReceivedBinding: ItemChatMessageReceivedBinding) :
        RecyclerView.ViewHolder(messageReceivedBinding.root) {
        fun bind(messageSent: MessageReceived) {
            with(messageReceivedBinding) {
                tvChatMessage.text = messageSent.message
                setBackground(messageSent.hasTail)
            }
        }

        fun bindHasTailChanged(hasTail: Boolean) {
            with(messageReceivedBinding) {
                setBackground(hasTail)
            }
        }

        private fun ItemChatMessageReceivedBinding.setBackground(hasTail: Boolean) {
            val tvBackground = if (hasTail) {
                R.drawable.bubble_tail_message_received
            } else {
                R.drawable.bubble_message_received
            }
            tvChatMessage.setBackgroundResource(tvBackground)
        }
    }
}
