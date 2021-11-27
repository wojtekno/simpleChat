package com.example.core.data

import com.example.core.domain.ChatMessage
import java.util.Calendar

class DummyData {

    fun createChatMessagesDummy(): List<ChatMessage> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -8)
        val list = mutableListOf<ChatMessage>()
        for (i in 0..100) {

            if (i % 3 == 0) {
                cal.add(Calendar.HOUR, 2)
            } else {
                cal.add(Calendar.SECOND, (10..30).random())
            }

            when {
                i % 4 == 0 -> {
                    list.add(
                        ChatMessage(
                            i, 1, 2,
                            "Some long text as a placeholder here. It is good to see how longer texts will behave. OMG!!! $i",
                            (i + 1) % 3 == 0,
                            cal.timeInMillis
                        )
                    )
                }
                else -> {
                    list.add(
                        ChatMessage(
                            i, 2, 1,
                            "Some long text as a placeholder here. It is good to see how longer texts will behave. OMG!!! $i",
                            i % 5 != 0,
                            cal.timeInMillis
                        )
                    )
                }
            }
        }
        return list
    }
}
