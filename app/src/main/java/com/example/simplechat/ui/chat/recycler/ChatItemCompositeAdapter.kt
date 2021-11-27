package com.example.simplechat.ui.chat.recycler

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.recycler.delegates.DelegateAdapter

class ChatItemCompositeAdapter(
    private val delegates: SparseArray<DelegateAdapter<ChatItem, RecyclerView.ViewHolder>>
) : ListAdapter<ChatItem, RecyclerView.ViewHolder>(ChatItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        for (i in 0 until delegates.size()) {
            if (delegates[i].modelClass == getItem(position).javaClass) {
                return delegates.keyAt(i)
            }
        }
        throw NullPointerException("Can not get viewType for position $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].createViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        onBindViewHolder(holder, position, mutableListOf())

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        val delegateAdapter = delegates[getItemViewType(position)]

        if (delegateAdapter != null) {
            val delegatePayloads = payloads.map { it as ChatItem.Payloadable }
            delegateAdapter.bindViewHolder(getItem(position), holder, delegatePayloads)
        } else {
            throw NullPointerException("can not find adapter for position $position")
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewAttachedToWindow(holder)
        super.onViewAttachedToWindow(holder)
    }

    class Builder {

        private var count: Int = 0
        private val delegates: SparseArray<DelegateAdapter<ChatItem, RecyclerView.ViewHolder>> = SparseArray()

        fun add(delegateAdapter: DelegateAdapter<out ChatItem, *>): Builder {
            delegates.put(count++, delegateAdapter as DelegateAdapter<ChatItem, RecyclerView.ViewHolder>)
            return this
        }

        fun build(): ChatItemCompositeAdapter {
            require(count != 0) { "Register at least one adapter" }
            return ChatItemCompositeAdapter(delegates)
        }
    }
}
