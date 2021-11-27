package com.example.simplechat.ui.chat.recycler.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.ui.chat.model.ChatItem

abstract class DelegateAdapter<M, in VH : RecyclerView.ViewHolder>(val modelClass: Class<out M>) {

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bindViewHolder(model: M, viewHolder: VH, payloads: List<ChatItem.Payloadable>)

    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit
    open fun onViewAttachedToWindow(viewHolder: VH) = Unit
}
