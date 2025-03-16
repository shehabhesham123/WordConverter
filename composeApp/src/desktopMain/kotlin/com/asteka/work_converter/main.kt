package com.asteka.work_converter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.asteka.interpreter.platform.html.interpreter.HTMLInterpreter

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "WordConverter",
    ) {

    }
}