package org.example.project.kouki

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
    ) {
        App()
    }
}
