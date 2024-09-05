package org.example.project.kouki.network.repository

interface ChatWebSocketRepository {
    suspend fun connect(receive: (String) -> Unit)
    suspend fun sendMessage(message: String)
    suspend fun close()
}
