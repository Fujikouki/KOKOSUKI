package org.example.project.kouki.network


import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.example.project.kouki.network.repository.ChatWebSocketRepository

class WebSocketClient : ChatWebSocketRepository {

    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    private var session: DefaultClientWebSocketSession? = null

    // MutableSharedFlowを使って非同期でデータをストリーム化
    private val _messages = MutableSharedFlow<String>()
    override val messages = _messages.asSharedFlow()

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun connect(r: (String) -> Unit) {
        try {
            client.webSocket(host = "192.168.11.4", port = 8080, path = "/we/chatRoom") {
                session =
                    client.webSocketSession(
                        host = "192.168.11.4",
                        port = 8080,
                        path = "/we/chatRoom"
                    )
                session?.let {
                    GlobalScope.launch {
                        try {
                            while (true) {
                                val frame = it.incoming.receive()
                                if (frame is Frame.Text) {
                                    r(frame.readText())
                                    _messages.emit(frame.readText())
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            session?.close()
                            it.close()
                        }
                    }
                }
            }
        } catch (e: Exception) {
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
        session?.close()
        client.close()
    }

}
