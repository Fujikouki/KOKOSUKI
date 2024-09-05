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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun RoomChatRoomScreen(
    viewModel: ChatScreenViewModel = viewModel { ChatScreenViewModel() }
) {

    DisposableEffect(viewModel) {
        println("★ DisposableEffect")
        viewModel.connect()
        onDispose {
            viewModel.close()
        }
    }

    val mssaageList by viewModel.messageList.collectAsState()

    val sendMessage by viewModel.sendMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chat Room") })
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    TextField(value = sendMessage, onValueChange = { viewModel.changeMessage(it) })
                },
                floatingActionButton = {
                    Button(onClick = {
                        println("sendMessage: $sendMessage")
                        GlobalScope.launch {
                            viewModel.sendMessage()
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
                items(mssaageList) { message ->
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
