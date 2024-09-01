package org.example.project.kouki.network.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccount(
    @SerialName("Email")
    val email: String,
    @SerialName("IconUrl")
    val iconUrl: String,
    @SerialName("Password")
    val password: String,
    @SerialName("Username")
    val username: String
)
