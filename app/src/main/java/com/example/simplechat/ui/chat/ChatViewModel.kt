package com.example.simplechat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.ChatMessage
import com.example.core.usecases.GetMessagesUseCase
import com.example.core.usecases.SendMessageUseCase
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.MessageReceived
import com.example.simplechat.ui.chat.model.MessageSent
import com.example.simplechat.ui.chat.model.TimeSection
import com.example.simplechat.util.DaysHoursFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val daysHoursFormatter: DaysHoursFormatter
) :
    ViewModel() {

    private var userId by Delegates.notNull<Int>()
    private var chatId by Delegates.notNull<Int>()
    private val hourInMilliseconds = 3600000
    private val twentySecondInMilliseconds = 20000
    private val _isChatInputActive = MutableLiveData(false)
    private lateinit var _chatItems: LiveData<List<ChatItem>>

    val isChatInputActive: LiveData<Boolean> = _isChatInputActive
    fun chatItems(): LiveData<List<ChatItem>> = _chatItems

    fun initChatItems() {
        if (!this::_chatItems.isInitialized) {
            _chatItems = getMessagesUseCase.execute(userId, chatId)
                .map { messages ->
                    return@map createUiModelList(messages)
                }.asLiveData()
        }
    }

    fun textChanged(chatInput: String) {
        _isChatInputActive.value = chatInput.isNotEmpty()
    }

    fun sendClicked(message: String) {
        _isChatInputActive.value = false
        viewModelScope.launch(Dispatchers.IO) {
            sendMessageUseCase.execute(ChatMessage(1, userId, chatId, message, false, System.currentTimeMillis()))
        }
    }

    fun setUpUserId(id: Int) {
        userId = id
    }

    fun setUpChatId(id: Int) {
        chatId = id
    }

    private fun createUiModelList(messages: List<ChatMessage>): List<ChatItem> {
        val list = mutableListOf<ChatItem>()
        val lastIndex = messages.size - 1
        for (i in 0..lastIndex) {
            val message = messages[i]
            if (i == 0 || isMoreThanHourGap(messages, i, message)) {
                list.add(
                    TimeSection(daysHoursFormatter.getDay(message.creationDate), daysHoursFormatter.getHour(messages[i].creationDate))
                )
            }
            val hasTail = hasTail(i, lastIndex, message, messages)
            if (message.senderId == chatId) {
                list.add(MessageReceived(message.id, message.message, hasTail))
            } else {
                list.add(MessageSent(message.id, message.message, message.isSeen, hasTail))
            }
        }
        return list
    }

    private fun isMoreThanHourGap(
        messages: List<ChatMessage>,
        i: Int,
        message: ChatMessage
    ) = messages[i - 1].creationDate + hourInMilliseconds < message.creationDate

    private fun hasTail(
        currentIndex: Int,
        lastIndex: Int,
        message: ChatMessage,
        messages: List<ChatMessage>
    ): Boolean {
        return when {
            currentIndex == lastIndex -> true
            messages[currentIndex + 1].senderId != message.senderId -> true
            messages[currentIndex + 1].creationDate > message.creationDate + twentySecondInMilliseconds -> true
            else -> false
        }
    }
}
