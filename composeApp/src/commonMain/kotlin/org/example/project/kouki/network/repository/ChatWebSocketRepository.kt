package org.example.project.kouki.network.repository

import kotlinx.coroutines.flow.Flow

interface ChatWebSocketRepository {
    suspend fun connect(r: (String) -> Unit)
    suspend fun sendMessage(message: String)
    suspend fun close()
    val messages: Flow<String>
}
