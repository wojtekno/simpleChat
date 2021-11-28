package com.example.simplechat.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.core.domain.ChatMessage
import com.example.core.usecases.GetMessagesUseCase
import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.MessageReceived
import com.example.simplechat.ui.chat.model.MessageSent
import com.example.simplechat.ui.chat.model.TimeSection
import com.example.simplechat.util.DaysHoursFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val getMessagesUseCase: GetMessagesUseCase, private val daysHoursFormatter: DaysHoursFormatter) :
    ViewModel() {

    private val hourInMilliseconds = 3600000
    private val twentySecondInMilliseconds = 20000

    private val _isChatInputActive = MutableLiveData(false)

    //    private val _chatItems = MutableLiveData<List<ChatItem>>()
    private val _chatItems = getMessagesUseCase.execute(1).map { messages ->
        messages
        Log.d("vm", "msg size : ${messages.size}")
        val chatId = 1
        val list = mutableListOf<ChatItem>()
        val lastIndex = messages.size - 1
        for (i in 0..lastIndex) {
            val message = messages[i]
            if (i == 0 || messages[i - 1].creationDate < message.creationDate - hourInMilliseconds) {
                list.add(TimeSection(daysHoursFormatter.getDay(message.creationDate), daysHoursFormatter.getHour(messages[i].creationDate)))
            }
            if (message.senderId == chatId) {
                val hasTail = hasTail(i, lastIndex, message, messages)
                list.add(MessageReceived(message.id, message.message, hasTail))
            } else {
                list.add(MessageSent(message.id, message.message, message.isSeen, hasTail(i, lastIndex, message, messages)))
            }
        }
        return@map list.toList()
    }.asLiveData()

    val isChatInputActive: LiveData<Boolean> = _isChatInputActive
    val chatItems: LiveData<List<ChatItem>> = _chatItems

    fun textChanged(chatInput: String) {
        _isChatInputActive.value = chatInput.isNotEmpty()
        Log.d("vm", chatInput)
    }

    fun sendClicked() {
        _isChatInputActive.value = false
        Log.d("vm", "sendClicked")
    }

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
