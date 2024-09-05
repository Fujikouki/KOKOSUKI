package org.example.project.kouki.di


import org.example.project.kouki.network.repository.AccountCreateRepository
import org.example.project.kouki.network.repository.AccountLogInRepository
import org.example.project.kouki.network.repository.ChatWebSocketRepository
import org.example.project.kouki.network.usecase.AccountCreateUseCase
import org.example.project.kouki.network.usecase.AccountLogInUseCase
import org.example.project.kouki.network.usecase.WebSocketClientUseCase
import org.koin.dsl.module


val appModule = module {
    single<ChatWebSocketRepository> { WebSocketClientUseCase() }
    single<AccountLogInRepository> { AccountLogInUseCase() }
    single<AccountCreateRepository> { AccountCreateUseCase() }
}
