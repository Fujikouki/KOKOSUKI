package org.example.project.kouki.network.repository

interface AuthenticationSaveRepository {
    fun tokenSaved(result: String): Boolean
}
