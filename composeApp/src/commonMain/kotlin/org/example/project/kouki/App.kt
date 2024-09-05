package org.example.project.kouki

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.project.kouki.network.AccountApi
import org.example.project.kouki.ui.accountScreen.AccountCreatingScreen
import org.example.project.kouki.ui.accountScreen.LoginScreen
import org.example.project.kouki.ui.chat.RoomChatRoomScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    val api by remember { mutableStateOf(AccountApi()) }

    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "KOKOSUKIアプリ",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.LOGIN.name
            ) {
                composable(route = Screen.LOGIN.name) {
                    LoginScreen(paddingValues = paddingValues, onClickLogInButton = {
                        GlobalScope.launch {
                            api.logIn(it)
                        }
                    }) {
                        navController.navigate(Screen.ACCOUNT_CREATING.name)
                    }
                }
                composable(route = Screen.ACCOUNT_CREATING.name) {
                    AccountCreatingScreen(paddingValues = paddingValues, onLogUpButton = {
                        GlobalScope.launch {
                            api.createAccount(it)
                        }
                    }, onClick = {
                        navController.navigate(Screen.LOGIN.name)
                    }, onChatButton = {
                        navController.navigate(Screen.CHAT.name)
                    })
                }
                composable(route = Screen.CHAT.name) {
                    RoomChatRoomScreen(paddingValues)
                }
            }
        }
    }
}

enum class Screen {
    LOGIN,
    ACCOUNT_CREATING,
    CHAT,
}
