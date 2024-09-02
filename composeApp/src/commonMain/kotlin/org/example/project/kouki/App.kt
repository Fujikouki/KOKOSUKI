package org.example.project.kouki

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.project.kouki.network.AccountApi
import org.example.project.kouki.ui.accountScreen.AccountCreatingScreen
import org.example.project.kouki.ui.accountScreen.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(DelicateCoroutinesApi::class)
@Composable
@Preview
fun App() {

    var flag by remember { mutableStateOf(false) }

    val api by remember { mutableStateOf(AccountApi()) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "KOKOSUKIアプリ",
                            style = MaterialTheme.typography.h4
                        )
                    }
                )
            }
        ) { paddingValues ->
            if (flag) {
                AccountCreatingScreen(paddingValues, onLogUpButton = {
                    GlobalScope.launch {
                        api.createAccount(it)
                    }
                }) {
                    flag = false
                }
            } else {
                LoginScreen(paddingValues, onClickLogInButton = {
                    GlobalScope.launch {
                        api.logIn(it)
                    }
                }) {
                    flag = true
                }
            }
        }
    }
}
