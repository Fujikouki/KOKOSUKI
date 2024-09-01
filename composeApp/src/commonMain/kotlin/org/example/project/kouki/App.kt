package org.example.project.kouki

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import org.example.project.kouki.ui.accountScreen.AccountCreatingScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
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
            AccountCreatingScreen(paddingValues)
        }
    }
}
