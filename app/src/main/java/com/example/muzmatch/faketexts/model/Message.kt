package com.example.muzmatch.faketexts.model

import java.util.*

data class Message(
    val message: String,
    val type: MessageType,
    val timeSent: Calendar,
    var hasTail: Boolean = false
)

enum class MessageType {
    SENT,
    RECEIVED,
    TIMESTAMP
}