package org.example.project.kouki.network.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Login(
    @SerialName("Email")
    val email: String,
    @SerialName("Password")
    val password: String
)
