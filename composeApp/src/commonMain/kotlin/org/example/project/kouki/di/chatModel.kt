package org.example.project.kouki.di


import org.example.project.kouki.database.repository.TokenSettingsRepository
import org.example.project.kouki.database.usecase.TokenSettingUseCase
import org.example.project.kouki.network.repository.AccountCreateRepository
import org.example.project.kouki.network.repository.AccountLogInRepository
import org.example.project.kouki.network.repository.ChatWebSocketRepository
import org.example.project.kouki.network.usecase.AccountCreateUseCase
import org.example.project.kouki.network.usecase.AccountLogInUseCase
import org.example.project.kouki.network.usecase.WebSocketClientUseCase
import org.koin.dsl.module


val appModule = module {
    single<ChatWebSocketRepository> { WebSocketClientUseCase(get()) }
    single<AccountLogInRepository> { AccountLogInUseCase(get()) }
    single<AccountCreateRepository> { AccountCreateUseCase(get()) }
    single<TokenSettingsRepository> { TokenSettingUseCase() }
}
