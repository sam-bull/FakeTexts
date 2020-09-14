package com.example.muzmatch.faketexts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    companion object {
        private const val SECTION_TIME = 3600000
        private const val TAIL_TIME = 20000
    }

    val messages = mutableListOf<Message>()

    private val _sendMessage = MutableLiveData<Event<String>>()
    val sendMessage: LiveData<Event<String>> = _sendMessage

    fun onClickSendMessage() {
        _sendMessage.postValue(Event(""))
    }

    fun sendMessage(text: String, now: Calendar = Calendar.getInstance()) {
        if (messages.isEmpty()) {
            messages.add(
                Message(
                    "Today, ${now.get(Calendar.HOUR_OF_DAY)}:${now.get(Calendar.MINUTE)}",
                    MessageType.TIMESTAMP,
                    now
                )
            )
        }
        if (now.timeInMillis - messages.last().timeSent.timeInMillis > SECTION_TIME) {
            messages.add(
                Message(
                    "Today, ${now.get(Calendar.HOUR_OF_DAY)}:${now.get(Calendar.MINUTE)}",
                    MessageType.TIMESTAMP,
                    now
                )
            )
        }
        checkTail(MessageType.SENT, now)
        messages.add(Message(text, MessageType.SENT, now, true))
        if (text.contains("hello")) {
            receiveMessage("hi", now)
        }
        if (text.contains("how's it going")) {
            receiveMessage("I'm good", now)
            receiveMessage("you?", now)
        }
    }

    private fun receiveMessage(text: String, now: Calendar) {
        checkTail(MessageType.RECEIVED, now)
        messages.add(Message(text, MessageType.RECEIVED, now, true))
    }

    private fun checkTail(type: MessageType, now: Calendar) {
        if (messages.last().type == type &&
            (now.timeInMillis - messages.last().timeSent.timeInMillis) <= TAIL_TIME) {
            messages.last().hasTail = false
        }
    }
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}