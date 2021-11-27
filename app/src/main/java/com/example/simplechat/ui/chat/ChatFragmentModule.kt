package com.example.simplechat.ui.chat

import com.example.simplechat.ui.chat.recycler.ChatItemCompositeAdapter
import com.example.simplechat.ui.chat.recycler.delegates.MessageReceivedDelegateAdapter
import com.example.simplechat.ui.chat.recycler.delegates.MessageSentDelegateAdapter
import com.example.simplechat.ui.chat.recycler.delegates.TimeSectioningDelegateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ChatFragmentModule {

    @Provides
    fun provideChatAdapter(): ChatItemCompositeAdapter {
        return ChatItemCompositeAdapter.Builder()
            .add(MessageSentDelegateAdapter())
            .add(MessageReceivedDelegateAdapter())
            .add(TimeSectioningDelegateAdapter())
            .build()
    }
}
