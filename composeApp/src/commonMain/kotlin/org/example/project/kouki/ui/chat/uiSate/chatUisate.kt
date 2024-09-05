package org.example.project.kouki.ui.chat.uiSate

sealed interface ChatUiState {
    object Loading : ChatUiState, UiState.Loading
    data class Success(
        val messageList: List<String>,
        val sendMassage: String,
    ) : ChatUiState, UiState.Success

    data class Error(val message: String) : ChatUiState, UiState.Error
}
