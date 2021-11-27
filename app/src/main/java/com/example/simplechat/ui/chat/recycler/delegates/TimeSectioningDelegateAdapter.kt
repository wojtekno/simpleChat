package com.example.simplechat.ui.chat.recycler.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.databinding.ItemSectioningBinding
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.TimeSection

class TimeSectioningDelegateAdapter :
    DelegateAdapter<TimeSection, TimeSectioningDelegateAdapter.TimeSectionViewHolder>(TimeSection::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val viewBinding = ItemSectioningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeSectionViewHolder(viewBinding)
    }

    override fun bindViewHolder(
        model: TimeSection,
        viewHolder: TimeSectioningDelegateAdapter.TimeSectionViewHolder,
        payloads: List<ChatItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class TimeSectionViewHolder(private val messageReceivedBinding: ItemSectioningBinding) :
        RecyclerView.ViewHolder(messageReceivedBinding.root) {
        fun bind(section: TimeSection) {
            with(messageReceivedBinding) {
                tvTimeSection.text = section.time
                tvDateSection.text = section.date
            }
        }
    }
}
