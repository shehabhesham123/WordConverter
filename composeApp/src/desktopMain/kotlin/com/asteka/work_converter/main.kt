package com.asteka.work_converter

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.asteka.render.platform.multiplatform.ComposeRender
import com.asteka.render.platform.multiplatform.compose_html.RenderResult
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
        var annotatedString by remember { mutableStateOf(AnnotatedString("")) }
        var inlineTextContentMap by remember { mutableStateOf(mapOf<String, InlineTextContent>()) }
        Component.interpret("C:\\Users\\shehab.hesham\\OneDrive - UC Group\\Desktop\\book_test.html") {
            annotatedString = it.annotatedString
            it.inlineTextContentMap.forEach {
                println("key: ${it.key}, value: ${it.value}")
            }
            inlineTextContentMap = it.inlineTextContentMap
        }
        App(annotatedString,inlineTextContentMap)
    }
}
/*
@Composable
fun counter(cnt: Int, onClick: () -> Unit) {
    Row {
        Text(cnt.toString())
        Button(onClick = { onClick() }) {
            Text("increase")
        }
    }
}*/

/*@Composable
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
}*/

// as Test
object Component : KoinComponent {
    private val interpreter: HTMLInterpreter by inject<HTMLInterpreter>()
    private val getWordDocLocally: GetWordDocLocally by inject<GetWordDocLocally>()

    fun interpret(path: String, onSuccess2: (RenderResult) -> Unit) {
        getWordDocLocally.invoke(path).fold(::onFailure) { onSuccess(it, onSuccess2) }
    }

    fun onFailure(failure: Failure) {
        println(failure)
    }

    fun onSuccess(wordDoc: WordDoc, onSuccess2: (RenderResult) -> Unit) {
        onSuccess2(ComposeRender(interpreter).render(wordDoc.content))
    }
}


/*
private fun getInlineTextContent(imageWidth: Float, imageHeight: Float): InlineTextContent {
        return InlineTextContent(
            placeholder = Placeholder(
                width = TextUnit(imageWidth, TextUnitType.Sp),
                height = TextUnit(imageHeight, TextUnitType.Sp),
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            ),
            children = {
                Image(
                    bitmap = convertImageToBitmap(image.decodingImage!!),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                )
            }
        )
    }

    fun convertImageToBitmap(image: ByteArray): ImageBitmap {
            return org.jetbrains.skia.Image.makeFromEncoded(image).toComposeImageBitmap()
        }

        return buildAnnotatedString {
            withAnnotation("Image", "Image") {
                withStyle(SpanStyle(fontFeatureSettings = "${encoding.value.tags!!.image}|${image.id}")) {
                    appendInlineContent(image.id, "O")
                }
            }
        }
*/