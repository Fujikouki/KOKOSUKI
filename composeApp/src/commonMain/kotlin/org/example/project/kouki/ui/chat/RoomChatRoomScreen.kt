package org.example.project.kouki.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.example.project.kouki.ui.chat.uiSate.ChatUiSateHolder
import org.example.project.kouki.ui.chat.uiSate.ChatUiState

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun RoomChatRoomScreen(
    onNavigate: () -> Unit,
    sateHolder: ChatUiSateHolder = viewModel { ChatScreenViewModel() }
) {
    DisposableEffect(Unit) {
        sateHolder.connect()
        onDispose {
            sateHolder.close()
        }
    }
    when (val state = sateHolder.uiSate) {
        is ChatUiState.Loading -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { onNavigate() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "チャット",
                                fontSize = 24.sp
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Loading",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CircularProgressIndicator()
                }
            }
        }
        is ChatUiState.Error -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { onNavigate() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "チャット",
                                fontSize = 24.sp
                            )
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error",
                        color = Color.Red,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { sateHolder.connect() }) {
                        Text(text = "Retry")
                    }
                }
            }

        }
        is ChatUiState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { onNavigate() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "チャット",
                                fontSize = 24.sp
                            )
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            TextField(
                                value = state.sendMassage,
                                onValueChange = { sateHolder.changeMessage(it) }
                            )
                        },
                        floatingActionButton = {
                            Button(onClick = { sateHolder.sendChatMessage() }) {
                                Text(text = "送る")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                val listState = rememberLazyListState()
                // メッセージリストが更新されるたびにリストの最後にスクロールする
                LaunchedEffect(state.messageList.size) {
                    if (state.messageList.isNotEmpty()) {
                        listState.animateScrollToItem(state.messageList.size - 1)
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(state.messageList) { message ->
                            ChatCard(message = message)
                        }
                    }
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
