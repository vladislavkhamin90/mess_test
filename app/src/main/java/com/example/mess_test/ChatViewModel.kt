package com.example.mess_test

import ChatWebSocket
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mess_test.model.Message

class ChatViewModel : ViewModel() {

    private val socket = ChatWebSocket()

    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> = _messages

    fun connect(username: String) {
        socket.connect(username) { message ->
            val current = _messages.value ?: emptyList()
            _messages.postValue(current + message)
        }
    }

    fun send(message: Message) {
        socket.send(message)
    }

    override fun onCleared() {
        socket.close()
    }
}
