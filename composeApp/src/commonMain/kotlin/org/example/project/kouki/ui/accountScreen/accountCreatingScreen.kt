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
fun AccountCreatingScreen(
    paddingValues: PaddingValues,
    onChatButton: () -> Unit,
    onLoginButton: () -> Unit,
    viewModel: AccountCreateViewModel = viewModel { AccountCreateViewModel() },
) {

    val uiSate by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "アカウント作成",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "名前",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiSate.userName,
            onValueChange = { viewModel.onUserNameChange(it) },
            label = { Text("名前") }
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
            value = uiSate.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("メールアドレス") }
        )

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
            value = uiSate.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("パスワード") }
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "IconURL",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiSate.iconUrl,
            onValueChange = { viewModel.onIconUrlChange(it) },
            label = { Text("IconURL") }
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { viewModel.createAccount() }) {
            Text(text = "アカウント作成")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { onLoginButton() }) {
            Text(text = "ログイン")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { onChatButton() }) {
            Text(text = "チャット")
        }

    }

}
