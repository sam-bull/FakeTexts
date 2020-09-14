package com.example.muzmatch.faketexts

import java.util.*

data class Message(
    val message: String,
    val type: MessageType,
    val timestamp: Calendar,
    var hasTail: Boolean = false
)

enum class MessageType {
    SENT,
    RECEIVED,
    TIMESTAMP
}