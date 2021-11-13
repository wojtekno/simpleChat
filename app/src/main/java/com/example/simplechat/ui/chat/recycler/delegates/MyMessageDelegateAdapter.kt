package com.example.simplechat.ui.chat.recycler.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.R
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.MyMessage

class MyMessageDelegateAdapter : DelegateAdapter<MyMessage, MyMessageDelegateAdapter.MyMessageViewHolder>(MyMessage::class.java) {


    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_my_message, parent, false)
        return MyMessageViewHolder(view)
    }

    override fun bindViewHolder(model: MyMessage, viewHolder: MyMessageViewHolder, payloads: List<ChatItem.Payloadable>) {
        when (val payload = payloads.firstOrNull() as? MyMessage.ChangePayload) {
            is MyMessage.ChangePayload.IsSeenChanged ->
                viewHolder.bindIsSeenChanged(payload.isSeen)

            is MyMessage.ChangePayload.HasTailChanged ->
                viewHolder.bindHasTailChanged(payload.hasTail)

            is MyMessage.ChangePayload.IsSeenAndHasTailChanged ->
                viewHolder.bindIsSeenHasTailChanged(payload.isSeen, payload.hasTail)

            else -> viewHolder.bind(model)
        }
    }

    inner class MyMessageViewHolder(private val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        fun bind(message: MyMessage) {
            with(viewItem) {

            }
        }

        fun bindIsSeenChanged(isSeen: Boolean) {

        }

        fun bindHasTailChanged(hasTail: Boolean) {

        }

        fun bindIsSeenHasTailChanged(isSeen: Boolean, hasTail: Boolean) {

        }

    }
}