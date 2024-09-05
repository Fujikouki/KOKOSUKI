package org.example.project.kouki.ui.accountScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    viewModel: AccountLoginViewModel = viewModel { AccountLoginViewModel() },
    movingToChat: () -> Unit,
    onClick: () -> Unit,
) {

    val uiState by viewModel.loginUiState.collectAsState()



    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "ログイン",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "メールアドレス",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiState.email,
            onValueChange = { viewModel.onLoginEmailChange(it) },
            label = { Text("メールアドレス") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "パスワード",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiState.password,
            onValueChange = { viewModel.onLoginPasswordChange(it) },
            label = { Text("パスワード") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = {
                viewModel.logIn(
                    isSuccess = {
                        movingToChat()
                    }
                )
            }) {
            Text(text = "ログイン")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { onClick() }) {
            Text(text = "新規登録")
        }
    }
}
