package org.example.project.kouki.ui.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.kouki.network.repository.ChatWebSocketRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatScreenViewModel() : ViewModel(), KoinComponent {

    private val chatWebSocketRepository: ChatWebSocketRepository by inject()

    init {
        connect()
    }

    var sendMessage = mutableStateOf("")

    val messageList = MutableStateFlow(listOf<String>())


    fun connect() {
        viewModelScope.launch {
            chatWebSocketRepository.connect() {
                messageList.value += it
            }
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            chatWebSocketRepository.sendMessage(sendMessage.value)
        }
    }

    fun changeMessage(message: String) {
        sendMessage.value = message
    }

    fun close() {
        viewModelScope.launch {
            chatWebSocketRepository.close()
        }
    }

}

