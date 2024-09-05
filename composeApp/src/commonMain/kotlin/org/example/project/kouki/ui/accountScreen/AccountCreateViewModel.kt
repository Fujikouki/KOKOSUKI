package org.example.project.kouki.ui.accountScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.kouki.network.data.CreateAccount
import org.example.project.kouki.network.repository.AccountCreateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AccountCreateViewModel() : ViewModel(), KoinComponent {

    private val accountCreateUseCase: AccountCreateRepository by inject()

    private val _uiSate = MutableStateFlow(AccountCreateUiState())

    var uiState = _uiSate.asStateFlow()
        private set

    fun onUserNameChange(userName: String) {
        _uiSate.value = _uiSate.value.copy(userName = userName)
    }

    fun onEmailChange(email: String) {
        _uiSate.value = _uiSate.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiSate.value = _uiSate.value.copy(password = password)
    }

    fun onIconUrlChange(iconUrl: String) {
        _uiSate.value = _uiSate.value.copy(iconUrl = iconUrl)
    }

    fun createAccount(isSuccess: () -> Unit) {
        viewModelScope.launch {
            accountCreateUseCase.createAccount(
                account = CreateAccount(
                    email = uiState.value.email,
                    iconUrl = uiState.value.iconUrl,
                    password = uiState.value.password,
                    username = uiState.value.userName
                ),
                onSuccess = {
                    println("★ AccountCreateViewModel createAccount onSuccess: $it")
                    isSuccess()
                },
                onError = {
                    println("★ AccountCreateViewModel createAccount onError: $it")
                }
            )
        }
    }
}

data class AccountCreateUiState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val iconUrl: String = ""
)
