package com.example.muzmatch.faketexts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzmatch.faketexts.extensions.toDayTimeString
import com.example.muzmatch.faketexts.model.Message
import com.example.muzmatch.faketexts.model.MessageType
import java.util.*
import javax.inject.Singleton

@Singleton
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
                    now.toDayTimeString(),
                    MessageType.TIMESTAMP,
                    now
                )
            )
        }
        if (now.timeInMillis - messages.last().timeSent.timeInMillis > SECTION_TIME) {
            messages.add(
                Message(
                    now.toDayTimeString(),
                    MessageType.TIMESTAMP,
                    now
                )
            )
        }
        checkTail(MessageType.SENT, now)
        messages.add(
            Message(
                text,
                MessageType.SENT,
                now,
                true
            )
        )
        if (text.contains("hello")) {
            receiveMessage("hi", now)
        }
        if (text.contains("how's it going")) {
            receiveMessage("I'm good", now)
            receiveMessage("you?", now)
        }
        if (text.contains("ice cream")) {
            receiveMessage("oooh what kind? I like the Aldi own brand 🍦", now)
        }
        if (text.contains("Ben & Jerrys")) {
            receiveMessage("ewww 🤮 I can't stand their stuff", now)
        }
    }

    private fun receiveMessage(text: String, now: Calendar) {
        checkTail(MessageType.RECEIVED, now)
        messages.add(
            Message(
                text,
                MessageType.RECEIVED,
                now,
                true
            )
        )
    }

    private fun checkTail(type: MessageType, now: Calendar) {
        if (messages.last().type == type &&
            (now.timeInMillis - messages.last().timeSent.timeInMillis) <= TAIL_TIME
        ) {
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