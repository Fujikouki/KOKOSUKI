package org.example.project.kouki

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import org.example.project.kouki.ui.accountScreen.AccountCreatingScreen
import org.example.project.kouki.ui.accountScreen.LoginScreen
import org.example.project.kouki.ui.chat.RoomChatRoomScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    val navController = rememberNavController()

    MaterialTheme {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Screen.LOGIN.name
        ) {
            composable(route = Screen.LOGIN.name) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = null
                                    )
                                }
                            },
                            title = {
                                Text(
                                    text = "ログイン",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        )
                    }
                ) { paddingValues ->
                    LoginScreen(paddingValues = paddingValues, movingToChat = {
                        navController.navigate(Screen.CHAT.name)
                    }) {
                        navController.navigate(Screen.ACCOUNT_CREATING.name)
                    }
                }
            }
            composable(route = Screen.ACCOUNT_CREATING.name) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = null
                                    )
                                }
                            },
                            title = {
                                Text(
                                    text = "アカウント作成",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        )
                    }
                ) { paddingValues ->
                    AccountCreatingScreen(
                        paddingValues = paddingValues,
                        movingToChat = {
                            navController.navigate(Screen.CHAT.name)
                        },
                        onLoginButton = {
                            navController.navigate(Screen.LOGIN.name)
                        })
                }
            }
            composable(route = Screen.CHAT.name) {
                RoomChatRoomScreen(onNavigate = {
                    navController.popBackStack()
                })
            }
        }

    }
}

enum class Screen {
    LOGIN,
    ACCOUNT_CREATING,
    CHAT,
}
