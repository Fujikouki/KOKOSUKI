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
import androidx.compose.runtime.setValue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.project.kouki.network.AccountApi
import org.example.project.kouki.ui.accountScreen.AccountCreatingScreen
import org.example.project.kouki.ui.accountScreen.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
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
                            style = MaterialTheme.typography.titleLarge
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
