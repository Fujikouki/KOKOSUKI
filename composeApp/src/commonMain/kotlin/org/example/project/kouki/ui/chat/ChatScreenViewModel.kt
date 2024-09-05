package org.example.project.kouki.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.kouki.network.repository.ChatWebSocketRepository
import org.example.project.kouki.ui.chat.uiSate.ChatUiSateHolder
import org.example.project.kouki.ui.chat.uiSate.ChatUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatScreenViewModel() : ViewModel(), KoinComponent, ChatUiSateHolder {

    override var uiSate: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    private val chatWebSocket: ChatWebSocketRepository by inject()

    private var _sendMessage = mutableStateOf("")

    private val _messageList = MutableStateFlow(listOf<String>())

    override fun connect() {
        uiSate = ChatUiState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatWebSocket.connect(
                    onConnect = {
                        uiSate = ChatUiState.Success(messageList = emptyList(), sendMassage = "")
                    },
                    onError = { throwable ->
                        println("Error: ${throwable.message}")
                        uiSate = ChatUiState.Error(message = throwable.message ?: "Unknown error")
                    }
                ) { message ->
                    _messageList.value += message
                    val currentState = uiSate as? ChatUiState.Success
                    if (currentState != null) {
                        uiSate = currentState.copy(
                            messageList = _messageList.value,
                            sendMassage = _sendMessage.value
                        )
                    }
                }
            }
        }
    }

    override fun sendChatMessage() {
        viewModelScope.launch {
            chatWebSocket.sendMessage(_sendMessage.value)
        }
    }

    override fun changeMessage(message: String) {
        _sendMessage.value = message
        uiSate = ChatUiState.Success(messageList = _messageList.value, sendMassage = message)
    }

    override fun close() {
        viewModelScope.launch {
            chatWebSocket.close()
        }
    }

}


