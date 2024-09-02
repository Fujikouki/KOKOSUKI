package org.example.project.kouki.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.project.kouki.network.WebSocketClient

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun RoomChatRoomScreen() {

    var sendMessage by remember { mutableStateOf("") }

    val messageList = remember { mutableStateListOf<String>() }

    val webSocket = remember {
        WebSocketClient()
    }

    DisposableEffect(Unit) {
        onDispose {
            GlobalScope.launch {
                webSocket.close()
            }
        }
    }

    LaunchedEffect(Unit) {
        webSocket.connect()
        // メッセージの収集を開始
        launch {
            webSocket.messages.collect { message ->
                messageList.add(message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chat Room") })
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    TextField(value = sendMessage, onValueChange = { sendMessage = it })
                },
                floatingActionButton = {
                    Button(onClick = {
                        println("sendMessage: $sendMessage")
                        GlobalScope.launch {
                            webSocket.sendMessage(sendMessage)
                        }
                    }
                    ) {
                        Text(text = "送る")
                    }
                }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(messageList) { message ->
                    ChatCard(message = message)
                }
            }

        }
    }
}


@Composable
fun ChatCard(message: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}
