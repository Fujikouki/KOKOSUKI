package org.example.project.kouki.network.repository

import org.example.project.kouki.network.data.Login

interface AccountLogInRepository {
    suspend fun logIn(
        login: Login,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )
}
