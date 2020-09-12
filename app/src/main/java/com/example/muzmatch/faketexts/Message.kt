package com.example.muzmatch.faketexts

import android.view.Gravity
import java.util.*

data class Message(val message: String, val type: MessageType, val timestamp: Calendar) {

    fun getBackground() = when(type) {
        MessageType.SENT -> R.drawable.pink_rounded_rectangle
        MessageType.RECEIVED -> R.drawable.grey_rounded_rectangle
        MessageType.TIMESTAMP -> null
    }

    fun getGravity() = when(type) {
        MessageType.SENT -> Gravity.END
            MessageType.RECEIVED -> Gravity.START
            MessageType.TIMESTAMP -> Gravity.CENTER_HORIZONTAL
    }

}

enum class MessageType {
    SENT,
    RECEIVED,
    TIMESTAMP
}