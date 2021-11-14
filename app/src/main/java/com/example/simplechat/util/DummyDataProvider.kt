package com.example.simplechat.util

import com.example.simplechat.ui.chat.model.ChatItem
import com.example.simplechat.ui.chat.model.MessageReceived
import com.example.simplechat.ui.chat.model.MessageSent

class DummyDataProvider {

    fun createDummy(): List<ChatItem> {
        val list = mutableListOf<ChatItem>()
        for (i in 0..45) {

            if (i % 4 == 0) {
                list.add(
                    MessageReceived(
                        i,
                        "Some long text as a placeholder here. It is good to see how longer texts will behave. OMG!!! $i",
                        (i + 1) % 3 == 0
                    )
                )
            } else {
                list.add(
                    MessageSent(
                        i,
                        "Some long text as a placeholder here. It is good to see how longer texts will behave. OMG!!! $i",
                        i % 5 != 0,
                        (i + 1) % 3 == 0
                    )
                )
            }

        }
        return list
    }
}