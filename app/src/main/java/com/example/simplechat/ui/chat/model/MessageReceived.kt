package com.example.simplechat.ui.chat.model

data class MessageReceived(val id: Int, val message: String, val hasTail: Boolean) : ChatItem {
    override fun id(): Int = id

    override fun content(): Any = MessageReceivedContent(hasTail)

    override fun payload(other: Any): ChatItem.Payloadable {
        return if (other is MessageReceivedContent) {
            when {
                hasTail != other.hasTail -> ChangePayload.HasTailChanged(other.hasTail)
                else -> ChatItem.Payloadable.None
            }
        } else {
            ChatItem.Payloadable.None
        }
    }

    inner class MessageReceivedContent(val hasTail: Boolean) {

        override fun equals(other: Any?): Boolean {
            return if (other is MessageReceivedContent) {
                hasTail == other.hasTail
            } else {
                false
            }
        }

        override fun hashCode(): Int {
            return 31 * hasTail.hashCode()
        }


    }

    sealed class ChangePayload : ChatItem.Payloadable {
        data class HasTailChanged(val hasTail: Boolean) : ChangePayload()
    }

}