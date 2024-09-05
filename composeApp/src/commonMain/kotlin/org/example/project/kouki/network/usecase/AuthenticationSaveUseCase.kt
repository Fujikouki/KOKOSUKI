package org.example.project.kouki.network.usecase

import org.example.project.kouki.network.repository.AuthenticationSaveRepository

class AuthenticationSaveUseCase : AuthenticationSaveRepository {
    override fun tokenSaved(result: String): Boolean {
        return result.isNotEmpty()
    }
}
