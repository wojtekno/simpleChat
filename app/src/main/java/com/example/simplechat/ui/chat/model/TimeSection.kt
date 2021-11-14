package com.example.simplechat.ui.chat.model

data class TimeSection(val time: String) : ChatItem {
    override fun id(): Int {
        return 1
    }

    override fun content(): Any {
        return this
    }
}
