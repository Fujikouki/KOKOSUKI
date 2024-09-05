package org.example.project.kouki.ui.chat.uiSate

interface ChatUiSateHolder {

    val uiSate: ChatUiState

    fun connect()

    fun sendChatMessage()

    fun changeMessage(message: String)

    fun close()

}
