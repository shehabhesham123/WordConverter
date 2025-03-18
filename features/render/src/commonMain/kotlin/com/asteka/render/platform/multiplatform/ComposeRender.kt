package com.asteka.render.platform.multiplatform

import androidx.compose.ui.text.AnnotatedString
import com.asteka.interpreter.core.Interpreter
import com.asteka.interpreter.platform.html.interpreter.HTMLInterpreterResult
import com.asteka.interpreter.platform.xml.interpreter.XMLInterpreterResult
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLAttributesStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLRender
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLTagStyleMapper

class ComposeRender(private val interpreter: Interpreter) {
    fun render(code:String): AnnotatedString {
        interpreter.interpret(code).apply {
            return when(this){
                is HTMLInterpreterResult ->{
                    val rootTag = this.body
                    ComposeHTMLRender(ComposeHTMLTagStyleMapper(), ComposeHTMLAttributesStyleMapper()).render(rootTag)
                }
                is XMLInterpreterResult -> { TODO("I will execute ComposeXMLRender") }
                else -> {throw Exception("Invalid Code")}
            }
        }
    }
}