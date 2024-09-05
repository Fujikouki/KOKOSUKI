package org.example.project.kouki.network.usecase


import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readReason
import io.ktor.websocket.readText
import org.example.project.kouki.network.repository.ChatWebSocketRepository

class WebSocketClientUseCase : ChatWebSocketRepository {

    private var client: HttpClient? = null
    private var session: DefaultClientWebSocketSession? = null

    override suspend fun connect(
        onConnect: () -> Unit,
        onError: (Throwable) -> Unit,
        receive: (String) -> Unit,
    ) {
        try {
            client = HttpClient(CIO) {
                install(WebSockets) {
                    pingInterval = 15_000
                    maxFrameSize = 10 * 1024 * 1024
                }

            }
            session =
                client!!.webSocketSession(host = "192.168.11.4", port = 8080, path = "/we/chatRoom")
            session?.let {
                onConnect()
                for (message in it.incoming) {
                    when (message) {
                        is Frame.Text -> {
                            val text = message.readText()
                            println("★ text: $text")
                            receive(text)
                        }

                        is Frame.Binary -> {
                            println("★ Binary Frame received")
                        }

                        is Frame.Close -> {
                            println("★ WebSocket closed: ${message.readReason()}")
                            throw Exception("WebSocket closed by server")
                        }

                        is Frame.Ping -> {
                            println("★ Ping Frame received")
                        }

                        is Frame.Pong -> {
                            println("★ Pong Frame received")
                        }

                        else -> {
                            println("★ Unknown frame type received")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("★ connect error: ${e.message}")
            onError(e)
            close()
        } finally {
            println("★ Session closed finally")
            onError(Exception("Session closed"))
            close()
        }
    }


    override suspend fun sendMessage(message: String) {
        try {
            session?.send(Frame.Text(message))
        } catch (e: Exception) {
            println("★ sendMessage error: ${e.message}")
            e.printStackTrace()
        }
    }

    override suspend fun close() {
        println("★ close")
        try {
            session?.close()
            client?.close()
        } catch (e: Exception) {
            println("★ close error: ${e.message}")
            e.printStackTrace()
        }
    }
}
