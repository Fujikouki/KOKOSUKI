package org.example.project.kouki.network.usecase

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.kouki.database.repository.TokenSettingsRepository
import org.example.project.kouki.network.data.Login
import org.example.project.kouki.network.repository.AccountLogInRepository

class AccountLogInUseCase(private val token: TokenSettingsRepository) : AccountLogInRepository {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    override suspend fun logIn(
        login: Login,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val result = runCatching {
            // 送信するデータの準備
            val logInRequest = login
            // POSTリクエストの送信
            //localhostの場合
            val response =
                client.post("http://192.168.11.4:8080/account/login") {
                    contentType(io.ktor.http.ContentType.Application.Json)
                    setBody(logInRequest)
                }
            response
        }
        result.onSuccess {
            val cookie = it.headers["Set-Cookie"]
            if (cookie != null) {
                token.saveToken(cookie)
                onSuccess(cookie)
            } else {
                token.deleteToken()
                onError("Error occurred: Cookie is null")
            }
        }.onFailure { exception ->
            token.deleteToken()
            onError("Error occurred: ${exception.message}")
        }
    }
}
