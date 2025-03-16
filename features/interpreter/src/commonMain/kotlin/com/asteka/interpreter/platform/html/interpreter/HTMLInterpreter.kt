package com.asteka.interpreter.platform.html.interpreter

import com.asteka.interpreter.core.Interpreter
import com.asteka.interpreter.core.InterpreterResult
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HTMLInterpreter(private val bodyInterpreter: BodyInterpreter, private val headInterpreter: HeadInterpreter) :
    Interpreter {
    override fun interpret(code: String): InterpreterResult {
        val doc: Document = Jsoup.parse(code)
        val head = headInterpreter.get(doc.head())
        val body = bodyInterpreter.parse(doc.body()) // Start from body
        return HTMLInterpreterResult(head, body)
    }
}