package com.example.simplechat.ui.chat.model

interface ChatItem {
    fun id(): Int

    fun content(): Any

    fun payload(other: Any): Payloadable = Payloadable.None

    interface Payloadable {
        object None : Payloadable
    }
}
