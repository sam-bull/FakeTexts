package com.example.muzmatch.faketexts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
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

    fun sendMessage(text: String) {
        val now = Calendar.getInstance()
        if (messages.isEmpty()) {
            messages.add(
                Message(
                    "Today, ${now.get(Calendar.HOUR_OF_DAY)}:${now.get(Calendar.MINUTE)}",
                    MessageType.TIMESTAMP,
                    now
                )
            )
        }
        if (now.timeInMillis - messages.last().timestamp.timeInMillis > SECTION_TIME) {
            messages.add(
                Message(
                    "Today, ${now.get(Calendar.HOUR_OF_DAY)}:${now.get(Calendar.MINUTE)}",
                    MessageType.TIMESTAMP,
                    now
                )
            )
        }
        checkTail(MessageType.SENT)
        messages.add(Message(text, MessageType.SENT, now, true))
        if (text.contains("hello")) {
            receiveMessage("hi")
        }
        if (text.contains("how's it going")) {
            receiveMessage("I'm good")
            receiveMessage("you?")
        }
    }

    private fun receiveMessage(text: String) {
        checkTail(MessageType.RECEIVED)
        messages.add(Message(text, MessageType.RECEIVED, Calendar.getInstance(), true))
    }

    private fun checkTail(type: MessageType) {
        if (messages.last().type == type && Calendar.getInstance().timeInMillis - messages.last().timestamp.timeInMillis < TAIL_TIME) {
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