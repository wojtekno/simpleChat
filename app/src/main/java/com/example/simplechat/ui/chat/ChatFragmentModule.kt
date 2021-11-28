package com.example.simplechat.ui.chat

import com.example.core.usecases.GetMessagesUseCase
import com.example.core.usecases.GetMessagesImpl
import com.example.core.usecases.SendMessageImpl
import com.example.core.usecases.SendMessageUseCase
import com.example.simplechat.ui.chat.recycler.ChatItemCompositeAdapter
import com.example.simplechat.ui.chat.recycler.delegates.MessageReceivedDelegateAdapter
import com.example.simplechat.ui.chat.recycler.delegates.MessageSentDelegateAdapter
import com.example.simplechat.ui.chat.recycler.delegates.TimeSectioningDelegateAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent

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

@Module
@InstallIn(ViewModelComponent::class)
abstract class ChatViewModelModule {

    @Binds
    abstract fun bindGetChatMessagesUseCase(getMessagesImpl: GetMessagesImpl): GetMessagesUseCase
    @Binds
    abstract fun bindSendMessagesUseCase(sendMessageImpl: SendMessageImpl): SendMessageUseCase
}
