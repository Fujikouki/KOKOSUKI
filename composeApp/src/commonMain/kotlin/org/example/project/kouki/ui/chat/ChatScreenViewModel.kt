package org.example.project.kouki.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val chatWebSocketRepository: ChatWebSocketRepository by inject()

    private var _sendMessage = mutableStateOf("")

    private val _messageList = MutableStateFlow(listOf<String>())

    override fun connect() {
        // 初期状態をLoadingに設定
        uiSate = ChatUiState.Loading
        viewModelScope.launch {
            withContext(kotlinx.coroutines.Dispatchers.IO) {
                // WebSocket接続
                chatWebSocketRepository.connect(
                    onConnect = {
                        // 接続成功時、状態をSuccessに設定
                        uiSate = ChatUiState.Success(messageList = emptyList(), sendMassage = "")
                    },
                    onError = { throwable ->
                        // エラー時にError状態に遷移
                        println("Error: ${throwable.message}")
                        uiSate = ChatUiState.Error(message = throwable.message ?: "Unknown error")
                    }
                ) { message ->
                    // メッセージを受信したらリストを更新
                    _messageList.value += message

                    // 現在の状態がSuccessであるか確認し、成功状態を維持しつつメッセージリストを更新
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
            chatWebSocketRepository.sendMessage(_sendMessage.value)
        }
    }

    override fun changeMessage(message: String) {
        _sendMessage.value = message
        uiSate = ChatUiState.Success(messageList = _messageList.value, sendMassage = message)
    }

    override fun close() {
        viewModelScope.launch {
            chatWebSocketRepository.close()
        }
    }

}


