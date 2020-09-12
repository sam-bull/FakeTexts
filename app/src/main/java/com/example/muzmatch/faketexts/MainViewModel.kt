package com.example.muzmatch.faketexts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val messages = mutableListOf<String>()

    private val _sendMessage = MutableLiveData<Event<String>>()
    val sendMessage: LiveData<Event<String>> = _sendMessage

    fun onClickSendMessage() {
        _sendMessage.postValue(Event(""))
    }

    fun sendMessage(text: String) {
        messages.add(text)
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