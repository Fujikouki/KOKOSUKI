package org.example.project.kouki.database.repository

import com.russhwolf.settings.Settings

interface TokenSettingsRepository {
    val tokenSettings: Settings
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun deleteToken()
}
