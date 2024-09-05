package org.example.project.kouki.database.usecase

import com.russhwolf.settings.Settings
import org.example.project.kouki.database.repository.TokenSettingsRepository

class TokenSettingUseCase : TokenSettingsRepository {

    override val tokenSettings: Settings = Settings()

    override suspend fun saveToken(token: String) {
        tokenSettings.putString("token", token)
    }

    override suspend fun getToken(): String {
        return tokenSettings.getString("token", "")
    }

}
