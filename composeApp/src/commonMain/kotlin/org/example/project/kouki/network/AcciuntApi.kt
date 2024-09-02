package org.example.project.kouki.network

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
import org.example.project.kouki.network.data.CreateAccount


class AccountApi {
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


    suspend fun createAccount(createAccount: CreateAccount) {
        val result = runCatching {
            // 送信するデータの準備
            val userRequest = createAccount
            // POSTリクエストの送信
            //localhostの場合
            val response =
                client.post("http://192.168.11.4:8080/account/create") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(userRequest)
                }
            response.headers.get("Set-Cookie")
        }
        result.onSuccess { response ->
            println("Response: $response")
        }.onFailure { exception ->
            println("Error occurred: ${exception.message}")
        }
    }
}
