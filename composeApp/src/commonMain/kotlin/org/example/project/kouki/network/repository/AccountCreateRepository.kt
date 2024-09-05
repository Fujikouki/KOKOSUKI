package org.example.project.kouki.network.repository

import org.example.project.kouki.network.data.CreateAccount

interface AccountCreateRepository {
    suspend fun createAccount(
        account: CreateAccount,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )
}
