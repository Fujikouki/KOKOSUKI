package org.example.project.kouki.ui.accountScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.kouki.network.data.Login
import org.example.project.kouki.network.repository.AccountLogInRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AccountViewModel() : ViewModel(), KoinComponent {

    private val accountUseCase: AccountLogInRepository by inject()

    private val _loginUiState = MutableStateFlow(LoginUiSate())
    var loginUiState = _loginUiState.asStateFlow()
        private set

    fun onLoginEmailChange(email: String) {
        _loginUiState.value = _loginUiState.value.copy(email = email)
    }

    fun onLoginPasswordChange(password: String) {
        _loginUiState.value = _loginUiState.value.copy(password = password)
    }

    fun logIn() {
        viewModelScope.launch {
            accountUseCase.logIn(
                login = Login(
                    email = loginUiState.value.email,
                    password = loginUiState.value.password
                ),
                onSuccess = {
                    println("★ AccountViewModel logIn onSuccess: $it")
                },
                onError = {
                    println("★ AccountViewModel logIn onError: $it")
                }
            )
        }
    }
}

data class LoginUiSate(
    val email: String = "",
    val password: String = ""
)

