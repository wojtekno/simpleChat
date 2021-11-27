package com.example.simplechat.ui.chat.recycler.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.R
import com.example.simplechat.databinding.ItemChatMyMessageBinding
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.MessageSent

class MessageSentDelegateAdapter : DelegateAdapter<MessageSent, MessageSentDelegateAdapter.MyMessageViewHolder>(MessageSent::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val viewBinding = ItemChatMyMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMessageViewHolder(viewBinding)
    }

    override fun bindViewHolder(model: MessageSent, viewHolder: MyMessageViewHolder, payloads: List<ChatItem.Payloadable>) {
        when (val payload = payloads.firstOrNull() as? MessageSent.ChangePayload) {
            is MessageSent.ChangePayload.IsSeenChanged ->
                viewHolder.bindIsSeenChanged(payload.isSeen)

            is MessageSent.ChangePayload.HasTailChanged ->
                viewHolder.bindHasTailChanged(payload.hasTail)

            is MessageSent.ChangePayload.IsSeenAndHasTailChanged ->
                viewHolder.bindIsSeenHasTailChanged(payload.isSeen, payload.hasTail)

            else -> viewHolder.bind(model)
        }
    }

    inner class MyMessageViewHolder(private val myMessageBinding: ItemChatMyMessageBinding) :
        RecyclerView.ViewHolder(myMessageBinding.root) {
        fun bind(messageSent: MessageSent) {
            with(myMessageBinding) {
                tvChatMessage.text = messageSent.message
                setIsSeenIcon(messageSent.isSeen)
                setBackground(messageSent.hasTail)
            }
        }

        fun bindIsSeenChanged(isSeen: Boolean) {
            with(myMessageBinding) {
                setIsSeenIcon(isSeen)
            }
        }

        fun bindHasTailChanged(hasTail: Boolean) {
            with(myMessageBinding) {
                setBackground(hasTail)
            }
        }

        fun bindIsSeenHasTailChanged(isSeen: Boolean, hasTail: Boolean) {
            with(myMessageBinding) {
                setIsSeenIcon(isSeen)
                setBackground(hasTail)
            }
        }

        private fun ItemChatMyMessageBinding.setBackground(hasTail: Boolean) {
            val tvBackground = if (hasTail) {
                R.drawable.bubble_tail_message_sent
            } else {
                R.drawable.bubble_message_sent
            }
            tvChatMessage.setBackgroundResource(tvBackground)
        }

        private fun ItemChatMyMessageBinding.setIsSeenIcon(isSeen: Boolean) {
            val isSeenIcon = if (isSeen) {
                R.drawable.ic_seen
            } else {
                R.drawable.ic_unseen
            }
            icIsSeen.setImageResource(isSeenIcon)
        }
    }
}
