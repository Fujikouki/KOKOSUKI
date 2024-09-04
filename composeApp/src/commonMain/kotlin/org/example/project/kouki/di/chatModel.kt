package org.example.project.kouki.di


import org.example.project.kouki.network.WebSocketClient
import org.example.project.kouki.network.repository.ChatWebSocketRepository
import org.koin.dsl.module


val appModule = module {
    single<ChatWebSocketRepository> { WebSocketClient() }
}
