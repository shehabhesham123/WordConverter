package com.asteka.interpreter.platform.xml.interpreter

import com.asteka.interpreter.core.InterpreterResult
import com.asteka.interpreter.platform.html.tag.HTMLTag

class XMLInterpreterResult(val head: HTMLTag, val body: HTMLTag) : InterpreterResult() {
}