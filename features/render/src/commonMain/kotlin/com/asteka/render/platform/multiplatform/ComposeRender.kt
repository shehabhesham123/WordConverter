package com.asteka.render.platform.multiplatform

import com.asteka.interpreter.core.Interpreter
import com.asteka.interpreter.platform.html.interpreter.HTMLInterpreterResult
import com.asteka.interpreter.platform.xml.interpreter.XMLInterpreterResult
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLAttributesStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLRender
import com.asteka.render.platform.multiplatform.compose_html.ComposeHTMLTagStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.RenderResult
import java.io.File

class ComposeRender(private val interpreter: Interpreter) {
    fun render(file: File): RenderResult {
        interpreter.interpret(file).apply {
            return when (this) {
                is HTMLInterpreterResult -> {
                    val rootTag = this.body
                    ComposeHTMLRender(ComposeHTMLTagStyleMapper(), ComposeHTMLAttributesStyleMapper()).render(rootTag)
                }

                is XMLInterpreterResult -> {
                    TODO("I will execute ComposeXMLRender")
                }

                else -> {
                    throw Exception("Invalid Code")
                }
            }
        }
    }
}