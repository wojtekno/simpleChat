package com.example.simplechat.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _isChatInputActive = MutableLiveData(false)
    val isChatInputActive: LiveData<Boolean> = _isChatInputActive

    fun textChanged(chatInput: String) {
        _isChatInputActive.value = chatInput.isNotEmpty()
        Log.d("vm", chatInput)
    }

    fun sendClicked() {
        _isChatInputActive.value = false
        Log.d("vm", "sendClicked")
    }
}
