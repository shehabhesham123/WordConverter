package com.asteka.work_converter

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
import kotlinx.coroutines.runBlocking
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
        runBlocking {
            Component.interpret("/home/shehab/Documents/new.txt")
        }

    }
}

// as Test
object Component : KoinComponent {
    private val interpreter: HTMLInterpreter by inject<HTMLInterpreter>()
    private val getWordDocLocally: GetWordDocLocally by inject<GetWordDocLocally>()

    suspend fun interpret(path:String) {
        getWordDocLocally.invoke(path).fold(::onFailure, ::onSuccess)
    }

    fun onFailure(failure: Failure) {
        println(failure)
    }

    fun onSuccess(wordDoc: WordDoc) {
        println((interpreter.interpret(wordDoc.content) as HTMLInterpreterResult).body)
    }
}