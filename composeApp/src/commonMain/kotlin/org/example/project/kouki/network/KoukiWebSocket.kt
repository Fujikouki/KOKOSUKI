package org.example.project.kouki.network


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

class WebSocketClient : ChatWebSocketRepository {

    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    private var session: DefaultClientWebSocketSession? = null

    override suspend fun connect(receive: (String) -> Unit) {
        println("★ connect")
        try {
            // WebSocketセッションを非同期で取得
            session =
                client.webSocketSession(host = "192.168.11.4", port = 8080, path = "/we/chatRoom")
            session?.let { session ->
                try {
                    while (true) {
                        for (message in session.incoming) {
                            when (message) {
                                is Frame.Text -> {
                                    val text = message.readText()
                                    println("★ text: $text")
                                    receive(text)
                                }

                                is Frame.Binary -> {
                                    // バイナリデータの処理を実装
                                    println("★ Binary Frame received")
                                }

                                is Frame.Close -> {
                                    println("★ WebSocket closed: ${message.readReason()}")
                                    break // WebSocketが閉じられたのでループを抜ける
                                }

                                is Frame.Ping -> {
                                    // Pingフレームに対する応答（Pong）を送信
                                    //session.send(Frame.Pong(message.buffer))
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
                    println("★ Error during receiving: ${e.message}")
                    e.printStackTrace()
                } finally {
                    session.close() // セッションを必ずクローズ
                    println("★ Session closed")
                }
            }
        } catch (e: Exception) {
            println("★ connect error: ${e.message}")
            e.printStackTrace()
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
        session?.close()
        client.close()
    }

}
