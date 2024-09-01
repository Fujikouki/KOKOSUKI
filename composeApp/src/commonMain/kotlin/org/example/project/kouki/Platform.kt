package org.example.project.kouki

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform