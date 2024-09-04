package org.example.project.kouki

import org.example.project.kouki.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    try {
        startKoin {
            println("★ initKoin")
            modules(appModule)
        }
    } catch (e: Exception) {
        println("★ initKoin error${e.message}")
        e.printStackTrace()
    }
}
