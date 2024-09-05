package org.example.project.kouki.network.repository

interface ChatWebSocketRepository {
    suspend fun connect(
        onConnect: () -> Unit,
        onError: (Throwable) -> Unit,
        receive: (String) -> Unit
    )
    suspend fun sendMessage(message: String)
    suspend fun close()
}
