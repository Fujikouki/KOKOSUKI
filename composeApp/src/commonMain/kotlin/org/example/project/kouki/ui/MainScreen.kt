package org.example.project.kouki.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.example.project.kouki.network.WebSocketClient


@Composable
fun MainScreen() {
    var name by remember { mutableStateOf("") }
    var webSocketMessage by remember { mutableStateOf(listOf("")) }
    val websocket = remember { WebSocketClient() }
    LaunchedEffect(key1 = Unit) {
        websocket.connect()
        websocket.messages.collect {
            webSocketMessage += it
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        Text(
            text = "Hello, World!",
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
        )
        Button(
            onClick = {
                runBlocking {
                    websocket.sendMessage(name)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Click me!")
        }
        LazyColumn {
            items(webSocketMessage) { message ->
                Text(text = message)
            }
        }
    }
}


