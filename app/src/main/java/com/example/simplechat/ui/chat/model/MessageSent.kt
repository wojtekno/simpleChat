package com.example.simplechat.ui.chat.model

data class MessageSent(val id: Int, val message: String, val isSeen: Boolean, val hasTail: Boolean) :
    ChatItem {
    override fun id() = id

    override fun content(): Any = MyMessageContent(isSeen, hasTail)

    override fun payload(other: Any): ChatItem.Payloadable {
        return if (other is MyMessageContent) {
            when {
                isSeen != other.isSeen && hasTail != other.hasTail -> ChangePayload.IsSeenAndHasTailChanged(other.isSeen, other.hasTail)
                isSeen != other.isSeen -> ChangePayload.IsSeenChanged(other.isSeen)
                hasTail != other.hasTail -> ChangePayload.HasTailChanged(other.hasTail)
                else -> ChatItem.Payloadable.None
            }
        } else {
            ChatItem.Payloadable.None
        }
    }

    inner class MyMessageContent(val isSeen: Boolean, val hasTail: Boolean) {

        override fun equals(other: Any?): Boolean {
            return if (other is MyMessageContent) {
                isSeen == other.isSeen && hasTail == other.hasTail
            } else {
                false
            }
        }

        override fun hashCode(): Int {
            var result = isSeen.hashCode()
            result = 31 * result + isSeen.hashCode()
            return result
        }
    }

    sealed class ChangePayload : ChatItem.Payloadable {
        data class IsSeenChanged(val isSeen: Boolean) : ChangePayload()
        data class HasTailChanged(val hasTail: Boolean) : ChangePayload()
        data class IsSeenAndHasTailChanged(val isSeen: Boolean, val hasTail: Boolean) : ChangePayload()
    }
}
