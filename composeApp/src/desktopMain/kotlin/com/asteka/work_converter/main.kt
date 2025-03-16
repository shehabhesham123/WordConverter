package com.asteka.work_converter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "WordConverter",
    ) {
        App()
    }
}