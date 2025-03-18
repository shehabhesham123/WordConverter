package com.asteka.work_converter

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.asteka.converter.core.failure.Failure
import com.asteka.converter.features.convert.app.di.InteractModule
import com.asteka.converter.features.convert.app.di.RepositoryModule
import com.asteka.converter.features.convert.domain.entity.WordDoc
import com.asteka.converter.features.convert.domain.interactor.GetWordDocLocally
import com.asteka.interpreter.platform.di.InterpreterModule
import com.asteka.interpreter.platform.html.interpreter.HTMLInterpreter
import com.asteka.interpreter.platform.html.interpreter.HTMLInterpreterResult
import com.asteka.render.platform.multiplatform.ComposeRender
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLAttributesStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLRender
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLTagStyleMapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun main() = application {

    startKoin {
        modules(InterpreterModule, InteractModule, RepositoryModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "WordConverter",
    ) {

//        var cnt by remember { mutableStateOf(0) }

        var annotatedString by remember { mutableStateOf(AnnotatedString("")) }
        Component.interpret("/home/shehab/Documents/html_code.txt") {
            annotatedString = it
        }
        App(annotatedString)
    }
}

@Composable
fun counter(cnt: Int, onClick: () -> Unit) {
    Row {
        Text(cnt.toString())
        Button(onClick = { onClick() }) {
            Text("increase")
        }
    }
}

@Composable
fun Test() {
    println("recompse")
    var annotatedString by mutableStateOf(AnnotatedString("Hello world"))
    var bool = false



    Component.interpret("/home/shehab/Documents/html_code.txt") {
        println(it.text)
        annotatedString = AnnotatedString("")
        println(annotatedString.text)
        bool = !bool
    }

    println("end")
}

// as Test
object Component : KoinComponent {
    private val interpreter: HTMLInterpreter by inject<HTMLInterpreter>()
    private val getWordDocLocally: GetWordDocLocally by inject<GetWordDocLocally>()

    fun interpret(path: String, onSuccess2: (AnnotatedString) -> Unit) {
        getWordDocLocally.invoke(path).fold(::onFailure, { onSuccess(it, onSuccess2) })
    }

    fun onFailure(failure: Failure) {
        println(failure)
    }

    fun onSuccess(wordDoc: WordDoc, onSuccess2: (AnnotatedString) -> Unit) {
        onSuccess2(ComposeRender(interpreter).render(wordDoc.content))
    }
}