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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.kouki.network.data.CreateAccount


@Composable
fun AccountCreatingScreen(
    paddingValues: PaddingValues,
    onLogUpButton: (CreateAccount) -> Unit,
    onClick: () -> Unit,
    onChatButton: () -> Unit,
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

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
            value = name,
            onValueChange = { name = it },
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
            value = email,
            onValueChange = { email = it },
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
            value = password,
            onValueChange = { password = it },
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
            value = iconUrl,
            onValueChange = { iconUrl = it },
            label = { Text("IconURL") }
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = {
                onLogUpButton(
                    CreateAccount(
                        email = email,
                        username = name,
                        iconUrl = iconUrl,
                        password = password
                    )
                )
            }) {
            Text(text = "アカウント作成")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { onClick() }) {
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
